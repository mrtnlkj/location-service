package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;

import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapper;
import sk.uniza.locationservice.bean.enums.UpdateStatus;

import static sk.uniza.locationservice.util.DurationUtils.durationBetween;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdater {

	private static final AtomicBoolean isUpdateRunning = new AtomicBoolean(false);

	private final OsmFileDownloader fileDownloader;
	private final Osm2pgsqlImporter osm2pgsqlImporter;
	private final UpdateRecordMarker updateRecordMarker;

	public void update(UpdateWrapper wrapper) {
		if (isUpdateRunning.getAndSet(true)) {
			log.debug("Data update is already IN PROGRESS. Skipping {}.", wrapper.getTrigger());
			return;
		}
		Instant start = Instant.now();
		UpdateStatus status = UpdateStatus.RUNNING;
		log.info("Data update {}, started by: {}.", status, wrapper.getTrigger());
		UpdateRecord update = null;
		try {
			update = updateRecordMarker.getOrCreateRunningUpdateRecord(wrapper);
			File file = fileDownloader.downloadOsmFile(wrapper.getUrl());
			int importExitCode = osm2pgsqlImporter.importFile(file);
			status = importExitCode == 0 ? UpdateStatus.FINISHED : UpdateStatus.FAILED;
		} catch (Exception e) {
			status = UpdateStatus.FAILED;
			log.error("ERROR: ", e);
		} finally {
			updateRecordMarker.markUpdateRecordAs(update, status);
			Instant end = Instant.now();
			isUpdateRunning.set(false);
			log.info("Data update {}. Total time elapsed: {}s.", status, durationBetween(start, end));
		}
	}

	public boolean isUpdateRunning() {
		return isUpdateRunning.get();
	}

}
