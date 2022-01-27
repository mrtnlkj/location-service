package sk.uniza.locationservice.service;

import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapperRequest;
import sk.uniza.locationservice.bean.enums.UpdateStatus;

public interface UpdateRecordMarker {

	UpdateRecord getOrCreateRunningUpdateRecord(UpdateWrapperRequest wrapper);

	public void markUpdateRecordAs(UpdateRecord update, UpdateStatus status);

	UpdateRecord getLatestUpdateRecord();
}
