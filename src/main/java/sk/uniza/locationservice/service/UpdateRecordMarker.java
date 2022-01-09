package sk.uniza.locationservice.service;

import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapper;
import sk.uniza.locationservice.bean.enums.UpdateStatus;

public interface UpdateRecordMarker {

	public UpdateRecord getOrCreateRunningUpdateRecord(UpdateWrapper wrapper);

	public void markUpdateRecordAs(UpdateRecord update, UpdateStatus status);
}
