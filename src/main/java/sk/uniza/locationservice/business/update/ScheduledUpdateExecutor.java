package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;

import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.scheduled-update.enabled", havingValue = "true")
public class ScheduledUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;

	@Scheduled(cron = "${location-service.update.scheduled-update.cron}")
	@Transactional
	public void execute() {
		final UpdateType type = UpdateType.SCHEDULED;
		log.debug("{} data update triggered.", type);
		updateExecutor.executeUpdate(wrapUpdateRequest(type));
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
