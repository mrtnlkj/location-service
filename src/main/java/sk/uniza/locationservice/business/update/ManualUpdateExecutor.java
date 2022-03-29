package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.uniza.locationservice.business.service.UpdateRecordService;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;
import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.controller.bean.request.RunUpdateRequest;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.repository.entity.UpdateRecord;

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
