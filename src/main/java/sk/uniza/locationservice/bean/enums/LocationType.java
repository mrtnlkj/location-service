package sk.uniza.locationservice.bean.enums;

import lombok.Getter;

import java.util.Locale;

import static java.util.Objects.nonNull;

@Getter
public enum LocationType {
	CITY,
	TOWN,
	VILLAGE,
	UNKNOWN;

	public static LocationType fromString(String value) {
		for (LocationType type : LocationType.values()) {
			String upperCaseValue = nonNull(value) ? value.toUpperCase(Locale.ROOT) : null;
			if (type.name().equals(upperCaseValue)) {
				return type;
			}
		}
		return LocationType.UNKNOWN;
	}

	public static String fromLocationType(LocationType type){
		return type.name();
	}
}
