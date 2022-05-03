package sk.uniza.locationservice.controller.bean.queryfilters;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

import sk.uniza.locationservice.controller.bean.enums.LocationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LocationsFilter extends LimitAndOffsetFilter {

	@Parameter(example = "11256", schema = @Schema(
			implementation = Long.class, example = "11256"),
			description = "Query only location object with specified unique identification parameter.")
	@Nullable
	private Long locationId;

	@Parameter(example = "Žilina", schema = @Schema(implementation = String.class, example = "Žilina"),
			description = "Filter locations by specified location name in Slovak language.")
	@Nullable
	private String nameSk;

	@Parameter(example = "Zilina", schema = @Schema(implementation = String.class, example = "Zilina"),
			description = "Filter locations by specified location name in English language.")
	@Nullable
	private String nameEn;

	@Parameter(example = "120.00", schema = @Schema(implementation = BigDecimal.class, example = "120.00"),
			description = "Query only locations that have area greater than specified **areaFrom** parameter in squared meters. Can be combined with **areaTo**.")
	@Nullable
	private BigDecimal areaFrom;

	@Parameter(example = "150.00", schema = @Schema(implementation = BigDecimal.class, example = "150.00"),
			description = "Query only locations that have area smaller than specified **areaTo** parameter in squared meters. Can be combined with **areaFrom**.")
	@Nullable
	private BigDecimal areaTo;

	@Parameter(example = "CITY", schema = @Schema(implementation = LocationType.class, example = "CITY"),
			description = "Query only locations with specified **type**  parameter.")
	@Nullable
	private LocationType type;

	@Parameter(example = "\"036 01\"", schema = @Schema(implementation = String.class, example = "\"036 01\""),
			description = "Filter locations by specified postal code of the location.")
	@Nullable
	private String postalCode;

	@Parameter(description = "Query only locations that include latitude of the given GPS coordinate.", example = "49.20586596333355")
	@Nullable
	@Range(min = -90, max = +90)
	private BigDecimal lat;
	@Parameter(description = "Query only locations that include longitude of a given GPS coordinate.", example = "18.762886535806317")
	@Nullable
	@Range(min = -180, max = +180)
	private BigDecimal lon;

	@Parameter(description = "If set to **true**, locations will include a GeoJson with location boundary.", example = "false")
	@Nullable
	private Boolean embedGeoJson;
}
