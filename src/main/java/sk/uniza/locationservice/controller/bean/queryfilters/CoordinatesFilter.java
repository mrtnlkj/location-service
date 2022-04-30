package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesFilter {

	@Parameter(required = true, description = "Latitude of the given GPS coordinate.", example = "\"49.20586596333355\"")
	@NotNull
	@Range(min = -90, max = +90)
	private BigDecimal lat;
	@Parameter(required = true, description = "Longitude of a given GPS coordinate.", example = "\"18.762886535806317\"")
	@NotNull
	@Range(min = -180, max = +180)
	private BigDecimal lon;
}
