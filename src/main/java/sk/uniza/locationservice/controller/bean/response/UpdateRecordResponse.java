package sk.uniza.locationservice.controller.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecordResponse {

	private Long updateId;
	private Instant startedTime;
	private Instant finishedTime;
	private String dataDownloadUrl;
	private UpdateStatus status;
	private UpdateTrigger trigger;
	private String description;
	private String failedReason;

}
