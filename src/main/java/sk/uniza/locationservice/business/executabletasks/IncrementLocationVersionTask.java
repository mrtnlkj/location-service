package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.business.service.LocationVersionService;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.repository.entity.LocationVersionEntity;

@Component
@RequiredArgsConstructor
public class IncrementLocationVersionTask implements ProcessingTask {

	private final LocationVersionService locationVersionService;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		LocationVersionEntity locationVersion = locationVersionService.createNewLocationVersionFromFinishedUpdate(wrapper.getUpdate().getUpdateId());
		return wrapper.toBuilder().locationVersion(locationVersion).build();
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.INCREMENT_LOCATION_VERSION;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.INCREMENT_LOCATION_VERSION.getOrder();
	}
}
