package sk.uniza.locationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.net.URL;
import java.time.Instant;

import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecord {

	@Id
	private Long updateId;
	private Instant startedTime;
	private Instant finishedTime;
	private URL dataDownloadUrl;
	private UpdateStatus status;
	private UpdateTrigger trigger;
	private String description;

	public static class UpdateRecordBuilder {

		public UpdateRecordBuilder buildRunningUpdate(URL url, UpdateTrigger trigger, String description) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(UpdateStatus.RUNNING)
					   .trigger(trigger)
					   .description(description);
		}

		public UpdateRecordBuilder buildRunningUpdate(URL url, UpdateTrigger trigger) {
			return this.startedTime(Instant.now())
					   .dataDownloadUrl(url)
					   .status(UpdateStatus.RUNNING)
					   .trigger(trigger);
		}

		public UpdateRecordBuilder markUpdateAs(UpdateStatus status) {
			return this.build().toBuilder()
					   .status(status)
					   .finishedTime(Instant.now());
		}
	}
}
