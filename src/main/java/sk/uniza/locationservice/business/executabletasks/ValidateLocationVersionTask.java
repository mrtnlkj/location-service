package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.business.service.LocationVersionService;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Component
@RequiredArgsConstructor
public class ValidateLocationVersionTask implements ProcessingTask {

	private final LocationVersionService locationVersionService;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		locationVersionService.validateLatestLocationVersion(wrapper.getLocationVersion());
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.VALIDATE_LOCATION_VERSION;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.VALIDATE_LOCATION_VERSION.getOrder();
	}
}
