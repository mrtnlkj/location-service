package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.business.service.LocationService;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessDistrictNamesTask implements UpdateTaskExecutable {

	private final LocationService locationService;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		locationService.processDistrictNames(wrapper.getLocationVersion().getVersionId());
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.PROCESS_DISTRICT_NAMES;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.PROCESS_DISTRICT_NAMES.getOrder();
	}

}
