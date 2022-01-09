package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

import sk.uniza.locationservice.bean.RunUpdateRequest;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapper;
import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.config.DataUpdaterProperties;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdater {

	private final DataUpdaterProperties dataUpoDataUpdaterProperties;
	private static final AtomicBoolean isDataInitialized = new AtomicBoolean(false);

	private final DataUpdateExecutor dataUpdateExecutor;
	private final UpdateRecordService updateRecordService;
	private final LocationService locationService;

	public UpdateRecord manualUpdate(RunUpdateRequest request) {
		final UpdateTrigger trigger = UpdateTrigger.MANUAL_UPDATE;
		log.debug("Data {} STARTED, request: {}", trigger, request);
		if (dataUpdateExecutor.isUpdateRunning()) {
			//TODO exception
			throw new RuntimeException("data is alreadyUpdating");
		}
		UpdateWrapper wrapper = UpdateWrapper.builder().fromRunUpdateRequest(request,
																			 dataUpoDataUpdaterProperties.getFileDownloader().getDownloadUrl())
											 .trigger(trigger)
											 .build();
		UpdateRecord update = updateRecordService.saveRunningUpdate(wrapper);
		dataUpdateExecutor.update(wrapper);
		return update;
	}

	@Scheduled(initialDelayString = "PT30M", fixedDelayString = "PT1H")
	public void scheduledRetryUpdate() {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_RETRY_UPDATE;
		log.debug("Data {} started.", trigger);
		UpdateRecord latestUpdate = updateRecordService.getLatestUpdateRecord();
		if (nonNull(latestUpdate) && UpdateStatus.FAILED.equals(latestUpdate.getStatus())) {
			UpdateWrapper wrapper = UpdateWrapper.builder()
												 .url(latestUpdate.getDataDownloadUrl())
												 .trigger(UpdateTrigger.SCHEDULED_RETRY_UPDATE)
												 .build();
			dataUpdateExecutor.update(wrapper);
		}
	}

	@Scheduled(fixedDelay = Long.MAX_VALUE)
	public void scheduledStartupUpdate() {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_STARTUP_UPDATE;
		log.debug("Data {} started.", trigger);

		UpdateWrapper wrapper = UpdateWrapper.builder()
											 .url(dataUpoDataUpdaterProperties.getFileDownloader().getDownloadUrl())
											 .trigger(trigger)
											 .build();
		final boolean updateEnabled = dataUpoDataUpdaterProperties.isUpdateAtStartupEnabled();
		final boolean forceUpdateEnabled = dataUpoDataUpdaterProperties.isForceUpdateAtStartupEnabled();
		if (isDataInitialized.getAndSet(true)) {
			if (forceUpdateEnabled) {
				log.debug("Data initialization at startup: FORCE UPDATE");
				dataUpdateExecutor.update(wrapper);
			} else if (updateEnabled) {
				Long locationsCount = locationService.getLocationsCount();
				if (locationsCount <= 0) {
					log.debug("Data initialization at startup: UPDATE");
					dataUpdateExecutor.update(wrapper);
				} else {
					log.debug("Data initialization at startup skipping. Actual location data count: {}.", locationsCount);
				}
			}
		}
	}

	@Scheduled(cron = "${location-service.data-updater.scheduled-cron-update}")
	public void scheduledUpdate() {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_UPDATE;
		log.debug("Data {} started.", trigger);
		UpdateWrapper wrapper = UpdateWrapper.builder()
											 .url(dataUpoDataUpdaterProperties.getFileDownloader().getDownloadUrl())
											 .trigger(trigger)
											 .build();
		dataUpdateExecutor.update(wrapper);
	}
}
