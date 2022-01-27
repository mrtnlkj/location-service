package sk.uniza.locationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class LocationVersion {

	@Id
	private Long versionId;
	private Instant validityFrom;
	private Instant validityTo;
	private String description;
	private Long updateId;

	public static class LocationVersionBuilder {

		public LocationVersion.LocationVersionBuilder fromFinishedUpdate(Long updateId) {
			return this.updateId(updateId);
		}

		public LocationVersion.LocationVersionBuilder makeVersionValid() {
			return this.validityFrom(Instant.now());
		}

		public LocationVersion.LocationVersionBuilder makeVersionInvalid() {
			return this.validityTo(Instant.now().minus(1, ChronoUnit.MINUTES));
		}

	}
}
