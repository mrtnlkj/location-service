package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import sk.uniza.locationservice.business.service.UpdateProcessingTaskService;
import sk.uniza.locationservice.business.service.UpdateService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCleaner {

	private final UpdateService updateService;
	private final UpdateProcessingTaskService updateProcessingTask;
	private final UpdateExecutor updateExecutor;

	@PostConstruct
	@Transactional
	public void finishUpdatesWithFailureAtStartup() {
		log.debug("Finishing previously running updates with FAILURE at startup.");
		if (!updateExecutor.isInProgress()) {
			updateService.finishUpdatesWithFailure();
			updateProcessingTask.finishUpdateProcessingTasksWithFailure();
		}
	}
}
