package sk.uniza.locationservice.business.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import sk.uniza.locationservice.business.FutureHandler;
import sk.uniza.locationservice.business.executabletasks.UpdateTaskExecutable;
import sk.uniza.locationservice.business.service.UpdateProcessingTaskService;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;
import sk.uniza.locationservice.common.util.ErrorResponseUtil;
import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.repository.UpdateRepository;
import sk.uniza.locationservice.repository.entity.UpdateEntity;
import sk.uniza.locationservice.repository.entity.UpdateProcessingTaskEntity;

import static sk.uniza.locationservice.common.util.DurationUtils.prettyPrintDurationBetween;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateExecutor {

	private final ApplicationContext ctx;
	private final FutureHandler futureHandler;

	private final UpdateProcessingTaskService updateProcessingTaskService;
	private final UpdateRepository updateRepository;
	private final UpdateProperties properties;
	private UpdateEntity currentUpdate;
	private UpdateProcessingTaskEntity currentTask;
	private UpdateTaskExecutable executor;
	private int currentTaskIndex;
	@Getter
	private Instant startedTime;
	@Getter
	private boolean inProgress = false;
	private List<UpdateTaskExecutable> updateTaskExecutables = new LinkedList<>();

	private int retryNumber = 0;

	@PostConstruct
	public void init() {
		this.updateTaskExecutables = ctx.getBeansOfType(UpdateTaskExecutable.class).values()
										.stream()
										.sorted(Comparator.comparing(UpdateTaskExecutable::getOrder)).collect(Collectors.toCollection(LinkedList::new));
	}

	public void abortManually() {
		final String reason = "manual request";
		abort(reason);
	}

	public void timeoutAbort() {
		final String reason = "exceeded timeout limit";
		abort(reason);
	}

	private void abort(String reason) {
		final String failedReason = String.format("Operation aborted due to: %s.", reason);
		log.debug("Aborting current running update,  {}.", failedReason);
		futureHandler.cancelTask();
		finish(ProcessingStatus.FAILED, failedReason);
	}

	public void executeUpdate(UpdateWrapper wrapper) {
		futureHandler.submitTask(() -> execute(wrapper));
	}

	private void execute(UpdateWrapper wrapper) {
		if (this.inProgress) {
			log.debug("Data update is already IN PROGRESS. Skipping {} data update.", wrapper.getType());
			return;
		}
		try {
			log.info("{} data update {}.", wrapper.getType(), ProcessingStatus.RUNNING);
			this.initExecutorState();
			if (wrapper.getUpdate() == null) {
				wrapper = wrapper.toBuilder().update(createNewUpdate(wrapper)).build();
			}
			currentUpdate = wrapper.getUpdate();
			executeTasks(wrapper);
		} catch (Exception e) {
			if (isRetryUpdateAvailable()) {
				log.debug("Trying to execute retry update for {} task, attempt: {}", executor.getUpdateTaskCode(), getAttempt());
				currentTaskIndex--;
				execute(wrapper);
			}
			finishWithFailure(e);
			log.error("ERROR: ", e);
			throw new LocationServiceException(ErrorType.UPDATE_FAILED);
		} finally {
			log.info("Data update {}. Total time elapsed: {}.", currentUpdate.getStatus(),
					 prettyPrintDurationBetween(currentUpdate.getStartedTime(), currentUpdate.getFinishedTime()));
		}
	}

	private void executeTasks(UpdateWrapper wrapper) throws Exception {
		while (isInProgress()) {
			if (currentTaskIndex >= updateTaskExecutables.size()) {
				finishWithSuccess();
				break;
			}
			executor = updateTaskExecutables.get(currentTaskIndex++);
			log.debug("Execution of step: {} started.", executor.getUpdateTaskCode());
			startTask(executor, currentUpdate.getUpdateId());

			wrapper = executor.execute(wrapper);

			finishTaskWithSuccess();
			log.debug("Execution of step: {} finished.", executor.getUpdateTaskCode());
		}
	}

	private UpdateEntity createNewUpdate(UpdateWrapper wrapper) {
		UpdateEntity entity = UpdateEntity.builder().buildRunningUpdate(wrapper.getUrl().toString(), wrapper.getType(), wrapper.getDescription()).build();
		entity = updateRepository.save(entity);
		return entity;
	}

	private void startTask(UpdateTaskExecutable handler, Long updateId) {
		currentTask = UpdateProcessingTaskEntity.builder().buildRunningTask(updateId, handler.getUpdateTaskCode(), getAttempt()).build();
		currentTask = updateProcessingTaskService.save(currentTask);
	}

	private Long getAttempt() {
		return (long) retryNumber + 1;
	}

	private void finishTaskWithSuccess() {
		finishTask(ProcessingStatus.FINISHED);
	}

	private void finishTask(ProcessingStatus status) {
		if (currentTask == null) {
			return;
		}
		currentTask = currentTask.toBuilder()
								 .finishWith(status)
								 .build();
		updateProcessingTaskService.save(currentTask);
		currentTask = null;
	}

	private void finishWithSuccess() {
		finish(ProcessingStatus.FINISHED, null);
	}

	private void finishWithFailure(Exception e) {
		String failedReason = ErrorResponseUtil.getEnhancedErrorMessageWithCause(ErrorType.UPDATE_FAILED.getErrorMessage(), e.getCause());
		finish(ProcessingStatus.FAILED, failedReason);
	}

	private void finish(ProcessingStatus status, String failedReason) {

		if (currentTask != null) {
			finishTask(status);
		}

		if (currentUpdate != null) {
			currentUpdate = currentUpdate.toBuilder()
										 .markUpdateAs(status)
										 .failedReason(failedReason)
										 .build();
			updateRepository.save(currentUpdate);
		}

		finishExecutorState();
	}

	private void initExecutorState() {
		inProgress = true;
		startedTime = Instant.now();
		currentTaskIndex = 0;
		currentUpdate = null;
		currentTask = null;
		retryNumber = 0;
		futureHandler.scheduleUpdateTimeout(this::timeoutAbort);
	}

	private void finishExecutorState() {
		inProgress = false;
		futureHandler.cancelUpdateTimeout();
	}

	private boolean isRetryUpdateAvailable() {
		return (properties.getRetryTaskProperties().isEnabled() && retryNumber <= properties.getRetryTaskProperties().getMaxRetries());
	}
}
