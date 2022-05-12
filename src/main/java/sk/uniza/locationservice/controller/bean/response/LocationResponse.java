package sk.uniza.locationservice.controller.bean.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sk.uniza.locationservice.controller.bean.enums.LocationType;
import sk.uniza.locationservice.mapper.StringToMapSerializer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

	private Long locationId;
	private Long versionId;
	private String nameSk;
	@Nullable
	private String nameEn;
	private BigDecimal area;
	private Long population;
	private String districtNameSk;
	private String districtNameEn;
	private String regionNameSk;
	private String regionNameEn;
	private String stateNameSk;
	private String stateNameEn;
	private String isIn;
	private String postalCode;
	private LocationType type;
	private BigDecimal lat;
	private BigDecimal lon;
	@JsonSerialize(using = StringToMapSerializer.class)
	private String boundary;

}
