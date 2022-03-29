package sk.uniza.locationservice.business.service;

import sk.uniza.locationservice.repository.entity.LocationVersion;

public interface LocationVersionManager {

	LocationVersion createNewLocationVersionFromFinishedUpdate(Long updateId);

	void prepareLatestLocationVersion(LocationVersion locationVersion);

}
