package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.repository.HelperRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImportLocationsTask implements UpdateTaskExecutable {

	private final HelperRepository locationService;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		long count = locationService.importLocationDataWithVersionAndGetInsertedRecordsCount(wrapper.getLocationVersion().getVersionId());
		log.debug("{} location records was imported.", count);
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.LOCATIONS_IMPORT;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.LOCATIONS_IMPORT.getOrder();
	}
}
