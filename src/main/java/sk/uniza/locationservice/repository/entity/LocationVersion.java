package sk.uniza.locationservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static sk.uniza.locationservice.repository.entity.LocationVersion.TABLE_NAME;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(TABLE_NAME)
public class LocationVersion {

	public static final String TABLE_NAME = "location_version";

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
