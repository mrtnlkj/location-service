package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import sk.uniza.locationservice.business.filedownloader.OsmFileDownloader;
import sk.uniza.locationservice.business.importer.Osm2pgsqlImporter;
import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.business.service.LocationVersionManager;
import sk.uniza.locationservice.business.service.UpdateRecordMarker;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;
import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.repository.entity.LocationVersion;
import sk.uniza.locationservice.repository.entity.UpdateRecord;

import static sk.uniza.locationservice.common.util.DurationUtils.prettyPrintDurationBetween;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateExecutor {

	private static final AtomicBoolean isUpdateRunning = new AtomicBoolean(false);

	private final OsmFileDownloader fileDownloader;
	private final Osm2pgsqlImporter osm2pgsqlImporter;
	private final UpdateRecordMarker updateRecordMarker;
	private final LocationVersionManager locationVersionManager;
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
		UpdateRecord update = new UpdateRecord();
		try {
			update = updateRecordMarker.getOrCreateRunningUpdateRecord(wrapper);
			File file = fileDownloader.downloadOsmFile(wrapper.getUrl());
			int importExitCode = osm2pgsqlImporter.importFile(file);
			status = importExitCode == 0 ? UpdateStatus.FINISHED : UpdateStatus.FAILED;
			this.importNewVersionOfLocationData(update, status);
		} catch (Exception e) {
			status = UpdateStatus.FAILED;
			log.error("ERROR: ", e);
			throw new LocationServiceException(ErrorType.JDBC_CONNECTION_FAILURE_ERROR);
		} finally {
			updateRecordMarker.markUpdateRecordAs(update, status);
			Instant end = Instant.now();
			isUpdateRunning.set(false);
			log.info("Data update {}. Total time elapsed: {}.", status, prettyPrintDurationBetween(start, end));
		}
		return CompletableFuture.allOf();
	}

	private void importNewVersionOfLocationData(UpdateRecord runningUpdate, UpdateStatus status) {
		if (!UpdateStatus.FINISHED.equals(status)) {
			log.debug("Can not import new version of location data, because update status != FINISHED. Actual status: {}", status);
			return;
		}
		Instant start = Instant.now();
		LocationVersion locationVersion = locationVersionManager.createNewLocationVersionFromFinishedUpdate(runningUpdate.getUpdateId());
		log.info("Import of location data started with version: {}.", locationVersion.getVersionId());
		Long recordsInsertedCount = locationService.importLocationDataWithVersionAndGetInsertedRecordsCount(locationVersion.getVersionId());
		locationVersionManager.prepareLatestLocationVersion(locationVersion);
		Instant stop = Instant.now();
		log.info("Import of location data finished. " +
						 "Total time elapsed: {}, " +
						 "total records inserted count: {}.", prettyPrintDurationBetween(start, stop),
				 recordsInsertedCount);

	}

	public UpdateRecord getLatestUpdateRecord() {
		return updateRecordMarker.getLatestUpdateRecord();
	}
}
