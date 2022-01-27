package sk.uniza.locationservice.bean;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.net.URL;

import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRecordFilter {

	@Nullable
	@Parameter(example = "FINISHED", schema = @Schema(type = "string", implementation = UpdateStatus.class),
			description = "Filter update records by specified status.")
	private UpdateStatus status;

	@Nullable
	@Parameter(example = "MANUAL_UPDATE", schema = @Schema(type = "string", implementation = UpdateTrigger.class),
			description = "Filter update records by specified trigger.")
	private UpdateTrigger trigger;

	@Nullable
	@Parameter(example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf", schema = @Schema(type = "string", example = "https://download.geofabrik.de/europe/slovakia-latest.osm.pbf"),
			description = "Filter update records by specified URL.")
	private URL url;

}
