package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sk.uniza.locationservice.repository.LocationVersionRepository;
import sk.uniza.locationservice.repository.entity.LocationVersionEntity;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationVersionService {

	private final LocationVersionRepository locationVersionRepository;

	public LocationVersionEntity save(LocationVersionEntity version) {
		log.debug("save({})", version);
		return locationVersionRepository.save(version);
	}

	public LocationVersionEntity getLatestLocationVersion() {
		return locationVersionRepository.getLatestValidLocationVersion();
	}

	public LocationVersionEntity createNewLocationVersionFromFinishedUpdate(Long updateId) {
		log.debug("createNewLocationVersionFromFinishedUpdate({})", updateId);
		LocationVersionEntity entity = LocationVersionEntity.builder().fromUpdate(updateId).build();
		return this.save(entity);
	}

	public void prepareLatestLocationVersion(LocationVersionEntity entity) {
		log.debug("prepareLatestLocationVersion({})", entity);
		this.invalidatePreviousLocationVersion();
		entity = entity.toBuilder().validate().build();
		this.save(entity);
	}

	private void invalidatePreviousLocationVersion() {
		LocationVersionEntity entity = this.getLatestLocationVersion();
		if (nonNull(entity)) {
			log.debug("Invalidating previous location version: {}", entity.getVersionId());
			entity = entity.toBuilder().invalidate().build();
			this.save(entity);
		}
	}
}
