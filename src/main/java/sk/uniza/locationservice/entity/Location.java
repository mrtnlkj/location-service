package sk.uniza.locationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

import sk.uniza.locationservice.controller.bean.enums.LocationType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(Location.TABLE_NAME)
public class Location {

	public static final String TABLE_NAME = "location";

	@Id
	private Long locationId;
	private Long versionId;
	private String nameSk;
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
	private String boundary;

}
