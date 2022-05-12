package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.repository.UpdateProcessingTaskRepository;
import sk.uniza.locationservice.repository.entity.UpdateProcessingTaskEntity;

@Service
@RequiredArgsConstructor
public class UpdateProcessingTaskService {

	private final UpdateProcessingTaskRepository updateProcessingTaskRepository;

	public UpdateProcessingTaskEntity save(UpdateProcessingTaskEntity entity) {
		return updateProcessingTaskRepository.save(entity);
	}

	public void finishUpdateProcessingTasksWithFailure() {
		updateProcessingTaskRepository.finishUpdateProcessingTasksWithFailure(ProcessingStatus.FAILED);
	}

	public List<UpdateProcessingTaskEntity> getTasksByUpdateId(Long updateId) {
		return updateProcessingTaskRepository.updateProcessingTasksByUpdateId(updateId);
	}
}
