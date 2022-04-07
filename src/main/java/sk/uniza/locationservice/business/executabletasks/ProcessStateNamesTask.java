package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Component
@RequiredArgsConstructor
public class ProcessStateNamesTask implements UpdateTaskExecutable {

	private final LocationService locationService;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		locationService.processStateNames(wrapper.getLocationVersion().getVersionId());
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.PROCESS_STATE_NAMES;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.PROCESS_STATE_NAMES.getOrder();
	}
}
