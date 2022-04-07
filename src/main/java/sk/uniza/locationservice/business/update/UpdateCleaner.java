package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sk.uniza.locationservice.business.service.UpdateProcessingTaskService;
import sk.uniza.locationservice.business.service.UpdateService;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCleaner {

	private final UpdateService updateService;
	private final UpdateProcessingTaskService updateProcessingTask;

	@Scheduled(fixedDelay = Long.MAX_VALUE)
	@Transactional
	public void finishUpdatesWithFailureAtStartup() {
		log.debug("Finishing previously running updates with FAILURE at startup.");
		updateService.finishUpdatesWithFailure();
		updateProcessingTask.finishUpdateProcessingTasksWithFailure();
	}
}
