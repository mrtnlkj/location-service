package sk.uniza.locationservice.business.updaterunner;

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
import sk.uniza.locationservice.controller.bean.request.ManualUpdateRequest;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.controller.bean.response.UpdateRecordResponse;
import sk.uniza.locationservice.mapper.UpdateRecordMapper;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "location-service.update.manual-update-executor.enabled", havingValue = "true")
public class ManualUpdateExecutor {

	private final UpdateProperties updateProperties;
	private final UpdateExecutor updateExecutor;
	private final UpdateRecordService updateRecordService;
	private final UpdateRecordMapper updateRecordMapper;

	@Transactional
	public UpdateRecordResponse triggerUpdate(ManualUpdateRequest request) {
		final UpdateTrigger trigger = UpdateTrigger.MANUAL_UPDATE;
		log.debug("Data {} triggered, request: {}", trigger, request);
		if (updateExecutor.isUpdateRunning()) {
			log.warn("Update is already in progress.");
			throw new LocationServiceException(ErrorType.UPDATE_ALREADY_RUNNING);
		}
		UpdateWrapperRequest wrapper = wrapUpdateRequest(trigger, request);
		UpdateRecordEntity update = updateRecordService.saveRunningUpdate(wrapper);
		updateExecutor.update(wrapper);
		return updateRecordMapper.map(update);
	}

	private UpdateWrapperRequest wrapUpdateRequest(UpdateTrigger trigger, ManualUpdateRequest request) {
		return UpdateWrapperRequest.builder().fromRunUpdateRequest(request, updateProperties.getFileDownloader().getDownloadUrl())
								   .trigger(trigger)
								   .build();
	}
}
