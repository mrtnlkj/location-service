package sk.uniza.locationservice.controller.bean.enums;

import lombok.Getter;

import static java.util.Objects.nonNull;

@Getter
public enum LocationType {
	CITY,
	TOWN,
	VILLAGE,
	UNKNOWN;

	public static LocationType fromString(String value) {
		for (LocationType type : LocationType.values()) {
			String upperCaseValue = nonNull(value) ? value.toUpperCase() : null;
			if (type.name().equals(upperCaseValue)) {
				return type;
			}
		}
		return LocationType.UNKNOWN;
	}
}
