package sk.uniza.locationservice.controller.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateProcessingTaskCode;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProcessingTaskResponse {

	private Long processingTaskId;
	private Instant startedTime;
	private Instant finishedTime;
	private ProcessingStatus status;
	private UpdateProcessingTaskCode taskCode;
	private Long updateId;
	private Long attempt;

}
