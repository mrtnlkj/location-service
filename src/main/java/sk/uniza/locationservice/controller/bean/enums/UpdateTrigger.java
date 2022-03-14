package sk.uniza.locationservice.controller.bean.enums;

import lombok.Getter;

@Getter
public enum UpdateTrigger {
	SCHEDULED_UPDATE,
	SCHEDULED_STARTUP_UPDATE,
	SCHEDULED_RETRY_UPDATE,
	MANUAL_UPDATE,
}
