package sk.uniza.locationservice.controller.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResponse {

	private Long updateId;
	private Instant startedTime;
	private Instant finishedTime;
	private String dataDownloadUrl;
	private ProcessingStatus status;
	private UpdateType type;
	private String description;
	private String failedReason;
	private List<UpdateProcessingTaskResponse> tasks;

}
