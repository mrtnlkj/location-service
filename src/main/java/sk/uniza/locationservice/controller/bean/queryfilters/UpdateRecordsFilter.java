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

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UpdateRecordsFilter extends LimitAndOffsetFilter {

	@Parameter(example = "FINISHED", schema = @Schema(description = "Final status of the update.", implementation = UpdateStatus.class, example = "FINISHED"))
	@Nullable
	private UpdateStatus status;

	@Parameter(example = "MANUAL_UPDATE",
			schema = @Schema(description = "Update trigger that started the update.", implementation = UpdateTrigger.class, example = "MANUAL_UPDATE"))
	@Nullable
	private UpdateTrigger trigger;

	@Parameter(example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf",
			schema = @Schema(description = "URL used for download update file, parameter can be also partial URL string", implementation = String.class,
					example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf"))
	@Nullable
	private String url;

	@Parameter(example = "2022-05-01T00:00:00Z",
			schema = @Schema(description = "dateStartedFrom in ISO 8601 UTC - filter updates only AFTER this date", implementation = Instant.class,
					example = "2022-05-01T00:00:00Z"))
	@Nullable
	private Instant dateStartedFrom;

}
