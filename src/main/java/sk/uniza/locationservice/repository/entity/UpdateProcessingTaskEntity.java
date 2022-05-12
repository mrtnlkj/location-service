package sk.uniza.locationservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(UpdateProcessingTaskEntity.TABLE_NAME)
public class UpdateProcessingTaskEntity {

	public static final String TABLE_NAME = "update_processing_task";

	@Id
	private Long processingTaskId;
	private Instant startedTime;
	private Instant finishedTime;
	private ProcessingStatus status;
	private UpdateProcessingTaskCode taskCode;
	private Long updateId;
	private Long attempt;

	public static class UpdateProcessingTaskEntityBuilder {

		public UpdateProcessingTaskEntity.UpdateProcessingTaskEntityBuilder buildRunningTask(Long updateId, UpdateProcessingTaskCode taskCode, Long attempt) {
			return this.startedTime(Instant.now())
					   .updateId(updateId)
					   .attempt(attempt)
					   .status(ProcessingStatus.RUNNING)
					   .taskCode(taskCode);
		}

		public UpdateProcessingTaskEntity.UpdateProcessingTaskEntityBuilder finishWith(ProcessingStatus status) {
			return this.build().toBuilder()
					   .finishedTime(Instant.now())
					   .status(status);
		}
	}

}
