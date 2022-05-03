package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationsWithinDistanceFilter {

	@NotNull
	@Valid
	private CoordinatesFilter coordinates;
	@Parameter(required = true, description = "Distance [m]", example = "100.00", schema = @Schema(implementation = BigDecimal.class, example = "100.00"))
	@NotNull
	@Min(0)
	private BigDecimal distance;
}
