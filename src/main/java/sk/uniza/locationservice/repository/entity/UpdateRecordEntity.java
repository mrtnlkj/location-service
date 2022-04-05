package sk.uniza.locationservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(UpdateRecordEntity.TABLE_NAME)
public class UpdateRecordEntity {

	public static final String TABLE_NAME = "update_record";

	@Id
	private Long updateId;
	private Instant startedTime;
	private Instant finishedTime;
	private String dataDownloadUrl;
	private UpdateStatus status;
	private UpdateTrigger trigger;
	private String description;
	private String failedReason;

	public static class UpdateRecordEntityBuilder {

		public UpdateRecordEntity.UpdateRecordEntityBuilder buildRunningUpdate(String url, UpdateTrigger trigger, String description) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(UpdateStatus.RUNNING)
					   .trigger(trigger)
					   .description(description);
		}

		public UpdateRecordEntity.UpdateRecordEntityBuilder buildRunningUpdate(String url, UpdateTrigger trigger) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(UpdateStatus.RUNNING)
					   .trigger(trigger);
		}

		public UpdateRecordEntity.UpdateRecordEntityBuilder markUpdateAs(UpdateStatus status) {
			return this.build().toBuilder()
					   .status(status)
					   .finishedTime(Instant.now());
		}
	}
}
