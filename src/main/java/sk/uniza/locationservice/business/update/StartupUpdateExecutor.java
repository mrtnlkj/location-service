package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.startup-update-executor.enabled", havingValue = "true")
public class StartupUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;
	private final LocationService locationService;

	@Scheduled(fixedDelay = Long.MAX_VALUE)
	@Transactional
	public void triggerUpdate() {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_STARTUP_UPDATE;
		final boolean forceUpdateEnabled = updateProperties.getStartupUpdateExecutor().isForceUpdateEnabled();
		if (forceUpdateEnabled) {
			log.debug("Data {} triggered. FORCE UPDATE", trigger);
			updateExecutor.update(wrapUpdateRequest(trigger));
		} else {
			Long locationsCount = locationService.getLocationsCount();
			if (locationsCount <= 0) {
				log.debug("Data {} triggered. UPDATE", trigger);
				updateExecutor.update(wrapUpdateRequest(trigger));
			} else {
				log.debug("Data {} SKIPPING. There are already {} locations in database.", trigger, locationsCount);
			}
		}
	}

	private URL getDefaultDownloadUrl() {
		return updateProperties.getFileDownloader().getDownloadUrl();
	}

	private UpdateWrapperRequest wrapUpdateRequest(UpdateTrigger trigger) {
		return UpdateWrapperRequest.builder()
								   .url(getDefaultDownloadUrl())
								   .trigger(trigger)
								   .build();
	}

}
