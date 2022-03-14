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

	@Nullable
	@Parameter(example = "\"421742\"", schema = @Schema(implementation = Long.class, example = "\"999\""),
			description = "Query only location object with specified unique identification parameter.")
	private Long locationId;

	@Nullable
	@Parameter(example = "Martin", schema = @Schema(implementation = String.class, example = "Martin"),
			description = "Filter locations by specified location SK name. Locations with SK name containing specified name will be returned.")
	private String nameSk;

	@Nullable
	@Parameter(example = "Martin", schema = @Schema(implementation = String.class, example = "Martin"),
			description = "Query only location objects that have name in EN language greater than specified areaFrom parameter in squared meters.")
	private String nameEn;

	@Nullable
	@Parameter(example = "\"120.00\"", schema = @Schema(implementation = BigDecimal.class, example = "\"120.00\""),
			description = "Query only location objects that have area greater than specified areaFrom parameter in squared meters. Can be combined with areaTo.")
	private BigDecimal areaFrom;

	@Nullable
	@Parameter(example = "\"150.00\"", schema = @Schema(implementation = BigDecimal.class, example = "\"150.00\""),
			description = "Query only location objects that have area smaller than specified areaTo parameter in squared meters. Can be combined with areaFrom.")
	private BigDecimal areaTo;

	@Nullable
	@Parameter(example = "CITY", schema = @Schema(implementation = LocationType.class, example = "CITY"),
			description = "Query only location objects with type specified areaTo parameter in squared meters.")
	private LocationType type;

	@Nullable
	@Parameter(example = "\"036 01\"", schema = @Schema(implementation = String.class, example = "\"036 01\""),
			description = "Filter locations by specified postal code of location. Locations with specified postal code will be returned.")
	private String postalCode;
}
