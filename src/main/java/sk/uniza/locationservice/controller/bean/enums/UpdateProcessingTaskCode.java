package sk.uniza.locationservice.controller.bean.enums;

import lombok.Getter;

@Getter
public enum UpdateProcessingTaskCode {
	OSM_FILE_LOAD,
	OSM2PGSQL_IMPORT,
	INCREMENT_LOCATION_VERSION,
	LOCATIONS_IMPORT,
	PROCESS_STATE_NAMES,
	PROCESS_STATE_NAMES_HELPER,
	PROCESS_REGION_NAMES,
	PROCESS_DISTRICT_NAMES,
	VALIDATE_LOCATION_VERSION,
	FINAL_CLEANUP,
	;

	public int getOrder() {
		return this.ordinal() + 1;
	}
}
