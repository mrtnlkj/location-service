package sk.uniza.locationservice.controller.bean.response;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

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
	@Parameter(description = "Description.", schema = @Schema(implementation = Instant.class))
	@Nullable
	private Instant finishedTime;
	private String dataDownloadUrl;
	private ProcessingStatus status;
	private UpdateType type;
	@Parameter(description = "Description.", example = "Update triggered manually")
	@Nullable
	private String description;
	private String failedReason;
	private List<UpdateProcessingTaskResponse> tasks;

}
