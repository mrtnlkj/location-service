package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.repository.HelperRepository;

@Component
@RequiredArgsConstructor
public class ProcessRegionNamesTask implements ProcessingTask {

	private final HelperRepository helperRepository;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		helperRepository.processRegionNames(wrapper.getLocationVersion().getVersionId());
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.PROCESS_REGION_NAMES;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.PROCESS_REGION_NAMES.getOrder();
	}
}
