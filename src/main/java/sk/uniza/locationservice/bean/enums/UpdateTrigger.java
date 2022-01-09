package sk.uniza.locationservice.bean.enums;

import lombok.Getter;

@Getter
public enum UpdateTrigger {
	SCHEDULED_UPDATE,
	SCHEDULED_STARTUP_UPDATE,
	MANUAL_UPDATE,
	SCHEDULED_RETRY_UPDATE,
	;
}
