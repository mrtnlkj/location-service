package sk.uniza.locationservice.bean;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

import sk.uniza.locationservice.bean.enums.LocationType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationsFilter {

	@Nullable
	@Parameter(example = "\"999\"", schema = @Schema(type = "string", example = "\"999\""),
			description = "Filter locations by specified unique location identification ID parameter.")
	private Long id;

	@Nullable
	@Parameter(example = "Martin", schema = @Schema(type = "string", implementation = String.class, example = "Martin"),
			description = "Filter locations by specified location SK name. Locations with SK name containing specified name will be returned.")
	private String nameSk;

	@Nullable
	@Parameter(example = "Martin", schema = @Schema(type = "string", implementation = String.class, example = "Martin"),
			description = "Filter locations by specified location EN name. Locations with EN name containing specified name will be returned.")
	private String nameEn;

	@Nullable
	@Parameter(example = "\"5.50\"", schema = @Schema(type = "string", implementation = String.class, example = "\"5.50\""),
			description = "Filter locations by areaFrom parameter in squared meters. Locations with area larger than specified parameter will be returned.")
	private BigDecimal areaFrom;

	@Nullable
	@Parameter(example = "\"5.50\"", schema = @Schema(type = "string", implementation = String.class, example = "\"5.50\""),
			description = "Filter locations by areaTo parameter in squared meters. Locations with area smaller than specified parameter will be returned.")
	private BigDecimal areaTo;

	@Nullable
	@Parameter(example = "CITY", schema = @Schema(type = "string", implementation = LocationType.class),
			description = "Filter locations by specified type of location. Locations with specified type will be returned.")
	private LocationType type;

	@Nullable
	@Parameter(example = "\"029 01\"", schema = @Schema(type = "string", implementation = String.class, example = "\"029 01\""),
			description = "Filter locations by specified postal code of location. Locations with specified postal code will be returned.")
	private String postalCode;

	@Nullable
	@Parameter(example = "\"10\"", schema = @Schema(type = "string", implementation = String.class, example = "\"10\""),
			description = "Limit returned locations records by specified limit parameter. There will be returned only given number of records.")
	private Long limit;

	@Nullable
	@Parameter(example = "\"0\"", schema = @Schema(type = "string", implementation = String.class, example = "\"0\""),
			description = "Offset returned locations records by specified offset parameter. There will be skipped given number of records before records will be returned.")
	private Long offset;

}
