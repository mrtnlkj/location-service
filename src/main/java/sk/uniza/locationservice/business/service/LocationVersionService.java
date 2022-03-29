package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sk.uniza.locationservice.repository.entity.LocationVersion;
import sk.uniza.locationservice.repository.LocationVersionRepository;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationVersionService implements LocationVersionManager {

	private final LocationVersionRepository locationVersionRepository;

	public LocationVersion save(LocationVersion version) {
		log.debug("save({})", version);
		return locationVersionRepository.save(version);
	}

	public LocationVersion getLatestLocationVersion() {
		return locationVersionRepository.getLatestValidLocationVersion();
	}

	@Override
	public LocationVersion createNewLocationVersionFromFinishedUpdate(Long updateId) {
		log.debug("createNewLocationVersionFromFinishedUpdate({})", updateId);
		LocationVersion locationVersion = LocationVersion.builder().fromFinishedUpdate(updateId).build();
		return this.save(locationVersion);
	}

	@Override
	public void prepareLatestLocationVersion(LocationVersion locationVersion) {
		log.debug("prepareLatestLocationVersion({})", locationVersion);
		this.invalidatePreviousLocationVersion();
		locationVersion = locationVersion.toBuilder().makeVersionValid().build();
		this.save(locationVersion);
	}

	private void invalidatePreviousLocationVersion() {
		LocationVersion latestLocationVersion = this.getLatestLocationVersion();
		if (nonNull(latestLocationVersion)) {
			log.debug("Invalidating previous location version: {}", latestLocationVersion.getVersionId());
			latestLocationVersion = latestLocationVersion.toBuilder().makeVersionInvalid().build();
			this.save(latestLocationVersion);
		}
	}
}
