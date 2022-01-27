package sk.uniza.locationservice.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	@Id
	private Long id;
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
	private String type;
	private BigDecimal lat;
	private BigDecimal lon;
	private String boundary;

}
