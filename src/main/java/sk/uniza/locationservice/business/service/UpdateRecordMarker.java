package sk.uniza.locationservice.business.service;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.repository.entity.UpdateRecord;

public interface UpdateRecordMarker {

	UpdateRecord getOrCreateRunningUpdateRecord(UpdateWrapperRequest wrapper);

	void markUpdateRecordAs(UpdateRecord update, UpdateStatus status);

	UpdateRecord getLatestUpdateRecord();
}
