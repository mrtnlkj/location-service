package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.net.URL;

import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.startup-update.enabled", havingValue = "true")
public class StartupUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;
	private final LocationService locationService;

	@PostConstruct
	@Transactional
	public void execute() {
		final UpdateType type = UpdateType.SCHEDULED_STARTUP;
		final boolean forceUpdateEnabled = updateProperties.getStartupUpdate().isForceUpdateEnabled();
		if (forceUpdateEnabled) {
			log.debug("{} data update triggered. FORCE", type);
			updateExecutor.executeUpdate(wrapUpdateRequest(type));
		} else {
			Long locationsCount = locationService.getLocationsCount();
			if (locationsCount <= 0) {
				log.debug("{} data update triggered.", type);
				updateExecutor.executeUpdate(wrapUpdateRequest(type));
			} else {
				log.debug("{} data update SKIPPING. There are already {} locations in database.", type, locationsCount);
			}
		}
	}

	private URL getDefaultDownloadUrl() {
		return updateProperties.getFileDownloader().getDownloadUrl();
	}

	private UpdateWrapper wrapUpdateRequest(UpdateType type) {
		return UpdateWrapper.builder()
							.url(getDefaultDownloadUrl())
							.type(type)
							.build();
	}

}
