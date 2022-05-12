package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.Instant;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateRecordsFilter extends LimitAndOffsetFilter {

	@Parameter(example = "FINISHED",
			schema = @Schema(implementation = ProcessingStatus.class, example = "FINISHED"),
			description = "Final status of the update.")
	@Nullable
	private ProcessingStatus status;

	@Parameter(example = "MANUAL",
			schema = @Schema(implementation = UpdateType.class, example = "MANUAL"),
			description = "Type of the update.")
	@Nullable
	private UpdateType type;

	@Parameter(example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf",
			schema = @Schema(implementation = String.class,
					example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf"),
			description = "URL used for download update file, parameter can be also partial URL string")
	@Nullable
	private String url;

	@Parameter(example = "2022-05-01T00:00:00Z",
			schema = @Schema(implementation = Instant.class,
					example = "2022-05-01T00:00:00Z"),
			description = "dateStartedFrom in ISO 8601 UTC - filter updates only AFTER this date")
	@Nullable
	private Instant dateStartedFrom;

}
