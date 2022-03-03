package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.uniza.locationservice.bean.RunUpdateRequest;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapperRequest;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.config.UpdateProperties;
import sk.uniza.locationservice.exception.ErrorType;
import sk.uniza.locationservice.exception.LocationServiceException;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.manual-update-executor.enabled", havingValue = "true")
public class ManualUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;
	private final UpdateRecordService updateRecordService;

	@Transactional
	public UpdateRecord triggerUpdate(RunUpdateRequest request) {
		final UpdateTrigger trigger = UpdateTrigger.MANUAL_UPDATE;
		log.debug("Data {} triggered, request: {}", trigger, request);
		if (updateExecutor.isUpdateRunning()) {
			log.warn("Update is already in progress.");
			throw new LocationServiceException(ErrorType.UPDATE_ALREADY_RUNNING);
		}
		UpdateWrapperRequest wrapper = wrapUpdateRequest(trigger, request);
		UpdateRecord update = updateRecordService.saveRunningUpdate(wrapper);
		updateExecutor.update(wrapper);
		return update;
	}

	private UpdateWrapperRequest wrapUpdateRequest(UpdateTrigger trigger, RunUpdateRequest request) {
		return UpdateWrapperRequest.builder().fromRunUpdateRequest(request, updateProperties.getFileDownloader().getDownloadUrl())
								   .trigger(trigger)
								   .build();
	}
}
