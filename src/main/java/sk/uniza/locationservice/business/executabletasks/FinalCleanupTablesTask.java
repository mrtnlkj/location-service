package sk.uniza.locationservice.business.executabletasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.repository.HelperRepository;

@Component
@RequiredArgsConstructor
public class FinalCleanupTablesTask implements UpdateTaskExecutable {

	private final HelperRepository helperRepository;

	@Override
	public UpdateWrapper execute(UpdateWrapper wrapper) {
		helperRepository.cleanupPublicSchemaTables();
		return wrapper;
	}

	@Override
	public UpdateProcessingTaskCode getUpdateTaskCode() {
		return UpdateProcessingTaskCode.FINAL_CLEANUP;
	}

	@Override
	public int getOrder() {
		return UpdateProcessingTaskCode.FINAL_CLEANUP.getOrder();
	}

}
