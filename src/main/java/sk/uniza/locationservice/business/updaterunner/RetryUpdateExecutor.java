package sk.uniza.locationservice.business.updaterunner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.retry-update-executor.enabled", havingValue = "true")
public class RetryUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;
	private final AtomicLong failedAttemptsCounter = new AtomicLong(0);

	@Scheduled(initialDelayString = "PT5M", fixedDelayString = "${location-service.update.retry-update-executor.duration-between-attempts}")
	@Transactional
	public void triggerUpdate() throws ExecutionException, InterruptedException {
		final UpdateTrigger trigger = UpdateTrigger.SCHEDULED_RETRY_UPDATE;
		UpdateRecordEntity latestUpdate = updateExecutor.getLatestUpdateRecord();
		if (nonNull(latestUpdate) && UpdateStatus.FAILED.equals(latestUpdate.getStatus())) {
			if (shouldRetryUpdate()) {
				long counter = failedAttemptsCounter.incrementAndGet();
				log.debug("Data {} triggered. Number of attempts: {}.", trigger, counter);
				updateExecutor.update(wrapUpdateRequest(trigger, latestUpdate.getDataDownloadUrl(), hydrateDescription(latestUpdate))).get();
				this.resetFailedAttemptsCounter();
			} else {
				log.debug("Data {} skipping. Max attempts count exceeded. ({})", trigger, updateProperties.getRetryUpdateExecutor().getMaxAttemptsCount());
			}
		}
	}

	private void resetFailedAttemptsCounter() {
		UpdateRecordEntity update = updateExecutor.getLatestUpdateRecord();
		if (nonNull(update) && UpdateStatus.FINISHED.equals(update.getStatus())) {
			log.debug("resetFailedAttemptsCounter, previous attempts value: {} -> new attempts value: 0.", failedAttemptsCounter.get());
			this.failedAttemptsCounter.set(0);
		}
	}

	private UpdateWrapperRequest wrapUpdateRequest(UpdateTrigger trigger, String url, String description) {
		return UpdateWrapperRequest.builder()
								   .url(url)
								   .trigger(trigger)
								   .description(description)
								   .build();
	}

	private boolean shouldRetryUpdate() {
		return updateProperties.getRetryUpdateExecutor().getMaxAttemptsCount().compareTo(failedAttemptsCounter.get()) >= 0;
	}

	private String hydrateDescription(UpdateRecordEntity updateRecord) {
		return "[oldUpdateId: " + updateRecord.getUpdateId() + "], " + updateRecord.getDescription();
	}

}
