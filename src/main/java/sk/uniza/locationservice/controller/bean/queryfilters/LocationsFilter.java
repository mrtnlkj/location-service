package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

import sk.uniza.locationservice.controller.bean.enums.LocationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationsFilter extends LimitAndOffsetFilter {

	@Parameter(example = "\"11256\"", schema = @Schema(
			implementation = Long.class, example = "\"11256\""),
			description = "Query only location object with specified unique identification parameter.")
	@Nullable
	private Long locationId;

	@Parameter(example = "Martin", schema = @Schema(implementation = String.class, example = "Martin"),
			description = "Filter locations by specified location SK name. Locations with SK name containing specified name will be returned.")
	@Nullable
	private String nameSk;

	@Parameter(example = "Martin", schema = @Schema(implementation = String.class, example = "Martin"),
			description = "Query only location objects that have name in EN language greater than specified areaFrom parameter in squared meters.")
	@Nullable
	private String nameEn;

	@Parameter(example = "\"120.00\"", schema = @Schema(implementation = BigDecimal.class, example = "\"120.00\""),
			description = "Query only location objects that have area greater than specified areaFrom parameter in squared meters. Can be combined with areaTo.")
	@Nullable
	private BigDecimal areaFrom;

	@Parameter(example = "\"150.00\"", schema = @Schema(implementation = BigDecimal.class, example = "\"150.00\""),
			description = "Query only location objects that have area smaller than specified areaTo parameter in squared meters. Can be combined with areaFrom.")
	@Nullable
	private BigDecimal areaTo;

	@Parameter(example = "CITY", schema = @Schema(implementation = LocationType.class, example = "CITY"),
			description = "Query only location objects with type specified areaTo parameter in squared meters.")
	@Nullable
	private LocationType type;

	@Parameter(example = "\"036 01\"", schema = @Schema(implementation = String.class, example = "\"036 01\""),
			description = "Filter locations by specified postal code of location. Locations with specified postal code will be returned.")
	@Nullable
	private String postalCode;
}
