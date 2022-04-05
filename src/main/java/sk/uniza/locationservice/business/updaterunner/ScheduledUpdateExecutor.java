package sk.uniza.locationservice.business.updaterunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.scheduled-update-executor.enabled", havingValue = "true")
public class ScheduledUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;

	@Scheduled(cron = "${location-service.update.scheduled-update-executor.cron}")
	@Transactional
	public void triggerUpdate() {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_UPDATE;
		log.debug("Data {} triggered.", trigger);
		updateExecutor.update(wrapUpdateRequest(trigger));
	}

	private URL getDefaultDownloadUrl() {
		return updateProperties.getFileDownloader().getDownloadUrl();
	}

	private UpdateWrapperRequest wrapUpdateRequest(UpdateTrigger trigger) {
		return UpdateWrapperRequest.builder()
								   .url(getDefaultDownloadUrl().toString())
								   .trigger(trigger)
								   .build();
	}

}
