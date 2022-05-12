package sk.uniza.locationservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(UpdateEntity.TABLE_NAME)
public class UpdateEntity {

	public static final String TABLE_NAME = "update";

	@Id
	private Long updateId;
	private Instant startedTime;
	private Instant finishedTime;
	private String dataDownloadUrl;
	private ProcessingStatus status;
	private UpdateType type;
	private String description;
	private String failedReason;

	@Transient
	private List<UpdateProcessingTaskEntity> tasks;

	public static class UpdateEntityBuilder {

		public UpdateEntity.UpdateEntityBuilder buildRunningUpdate(String url, UpdateType type, String description) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(ProcessingStatus.RUNNING)
					   .type(type)
					   .description(description);
		}

		public UpdateEntity.UpdateEntityBuilder buildRunningUpdate(String url, UpdateType type) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(ProcessingStatus.RUNNING)
					   .type(type);
		}

		public UpdateEntity.UpdateEntityBuilder markUpdateAs(ProcessingStatus status) {
			return this.build().toBuilder()
					   .status(status)
					   .finishedTime(Instant.now());
		}
	}
}
