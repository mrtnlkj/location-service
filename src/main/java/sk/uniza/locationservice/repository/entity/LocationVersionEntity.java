package sk.uniza.locationservice.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(LocationVersionEntity.TABLE_NAME)
public class LocationVersionEntity {

	public static final String TABLE_NAME = "location_version";

	@Id
	private Long versionId;
	private Instant validityFrom;
	private Instant validityTo;
	private String description;
	private Long updateId;

	public static class LocationVersionEntityBuilder {

		public LocationVersionEntity.LocationVersionEntityBuilder fromUpdate(Long updateId) {
			return this.updateId(updateId);
		}

		public LocationVersionEntity.LocationVersionEntityBuilder validate() {
			return this.validityFrom(Instant.now());
		}

		public LocationVersionEntity.LocationVersionEntityBuilder invalidate() {
			return this.validityTo(Instant.now().minus(1, ChronoUnit.SECONDS));
		}

	}
}
