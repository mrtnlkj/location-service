package sk.uniza.locationservice.service;

import sk.uniza.locationservice.bean.LocationVersion;

public interface LocationVersionManager {

	LocationVersion createNewLocationVersionFromFinishedUpdate(Long updateId);

	void prepareLatestLocationVersion(LocationVersion locationVersion);

}
