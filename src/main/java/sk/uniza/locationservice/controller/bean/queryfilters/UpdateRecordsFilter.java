package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.net.URL;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordsFilter {

	@Nullable
	@Parameter(example = "FINISHED", schema = @Schema(type = "string", implementation = UpdateStatus.class),
			description = "Filter update records by specified status parameter.")
	private UpdateStatus status;

	@Nullable
	@Parameter(example = "MANUAL_UPDATE", schema = @Schema(type = "string", implementation = UpdateTrigger.class),
			description = "Filter update records by specified trigger parameter.")
	private UpdateTrigger trigger;

	@Nullable
	@Parameter(example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf",
			schema = @Schema(type = "string", example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf"),
			description = "Filter update records by specified URL parameter.")
	private URL url;

	@Nullable
	@Parameter(example = "\"10\"", schema = @Schema(type = "string", implementation = String.class, example = "\"10\""),
			description = "Limit returned update records by specified limit parameter. There will be returned only given number of records.")
	private Long limit;

	@Nullable
	@Parameter(example = "\"0\"", schema = @Schema(type = "string", implementation = String.class, example = "\"0\""),
			description = "Offset returned update records by specified offset parameter. There will be skipped given number of records before records will be returned.")
	private Long offset;
}
