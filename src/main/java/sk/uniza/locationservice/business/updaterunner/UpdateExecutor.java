package sk.uniza.locationservice.business.updaterunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import sk.uniza.locationservice.business.filedownloader.OsmFileDownloader;
import sk.uniza.locationservice.business.importer.Osm2pgsqlImporter;
import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.business.service.LocationVersionService;
import sk.uniza.locationservice.business.service.UpdateRecordService;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;
import sk.uniza.locationservice.common.util.ErrorResponseUtil;
import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.repository.entity.LocationVersionEntity;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

import static sk.uniza.locationservice.common.util.DurationUtils.prettyPrintDurationBetween;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateExecutor {

	private static final AtomicBoolean isUpdateRunning = new AtomicBoolean(false);

	private final OsmFileDownloader fileDownloader;
	private final Osm2pgsqlImporter osm2pgsqlImporter;
	private final UpdateRecordService updateRecordService;
	private final LocationVersionService locationVersionService;
	private final LocationService locationService;

	public boolean isUpdateRunning() {
		return isUpdateRunning.get();
	}

	@Async
	public CompletableFuture<Void> update(UpdateWrapperRequest wrapper) {
		if (isUpdateRunning.getAndSet(true)) {
			log.debug("Data update is already IN PROGRESS. Skipping {}.", wrapper.getTrigger());
			CompletableFuture.allOf();
		}
		Instant start = Instant.now();
		UpdateStatus status = UpdateStatus.RUNNING;
		log.info("Data update {}, started by: {}.", status, wrapper.getTrigger());
		UpdateRecordEntity update = new UpdateRecordEntity();
		String failedReason = null;
		try {
			update = updateRecordService.getOrCreateRunningUpdateRecord(wrapper);
			File file = fileDownloader.downloadOsmFile(new URL(wrapper.getUrl()));
			int importExitCode = osm2pgsqlImporter.importFile(file);
			status = importExitCode == 0 ? UpdateStatus.FINISHED : UpdateStatus.FAILED;
			this.importNewVersionOfLocationData(update, status);
		} catch (Exception e) {
			status = UpdateStatus.FAILED;
			failedReason = ErrorResponseUtil.getEnhancedErrorMessageWithCause(ErrorType.UPDATE_FAILED.getErrorMessage(), e.getCause());
			log.error("ERROR: ", e);
			throw new LocationServiceException(ErrorType.UPDATE_FAILED);
		} finally {
			updateRecordService.markUpdateRecordAs(update, status, failedReason);
			Instant end = Instant.now();
			isUpdateRunning.set(false);
			log.info("Data update {}. Total time elapsed: {}.", status, prettyPrintDurationBetween(start, end));
		}
		return CompletableFuture.allOf();
	}

	private void importNewVersionOfLocationData(UpdateRecordEntity runningUpdate, UpdateStatus status) {
		if (!UpdateStatus.FINISHED.equals(status)) {
			log.debug("Can not import new version of location data, because update status != FINISHED. Actual status: {}", status);
			return;
		}
		Instant start = Instant.now();
		LocationVersionEntity locationVersionEntity = locationVersionService.createNewLocationVersionFromFinishedUpdate(runningUpdate.getUpdateId());
		log.info("Import of location data started with version: {}.", locationVersionEntity.getVersionId());
		Long recordsInsertedCount = locationService.importLocationDataWithVersionAndGetInsertedRecordsCount(locationVersionEntity.getVersionId());
		locationVersionService.prepareLatestLocationVersion(locationVersionEntity);
		Instant stop = Instant.now();
		log.info("Import of location data finished. " +
						 "Total time elapsed: {}, " +
						 "total records inserted count: {}.", prettyPrintDurationBetween(start, stop),
				 recordsInsertedCount);

	}

	public UpdateRecordEntity getLatestUpdateRecord() {
		return updateRecordService.getLatestUpdate();
	}
}
