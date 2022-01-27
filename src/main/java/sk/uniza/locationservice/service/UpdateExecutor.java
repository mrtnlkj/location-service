package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import sk.uniza.locationservice.bean.LocationVersion;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapperRequest;
import sk.uniza.locationservice.bean.enums.UpdateStatus;

import static sk.uniza.locationservice.util.DurationUtils.durationBetween;

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
		} finally {
			updateRecordMarker.markUpdateRecordAs(update, status);
			Instant end = Instant.now();
			isUpdateRunning.set(false);
			log.info("Data update {}. Total time elapsed: {}s.", status, durationBetween(start, end));
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
						 "Total time elapsed: {}s, " +
						 "total records inserted count: {}.", durationBetween(start, stop),
				 recordsInsertedCount);

	}

	public UpdateRecord getLatestUpdateRecord() {
		return updateRecordMarker.getLatestUpdateRecord();
	}
}
