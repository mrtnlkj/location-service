package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sk.uniza.locationservice.repository.LocationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;

	public Long getLocationsCount() {
		return locationRepository.getLocationsCount();
	}

	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(Long versionId) {
		log.debug("importLocationDataWithVersionAndGetInsertedRecordsCount({})", versionId);
		return locationRepository.importLocationDataWithVersionAndGetInsertedRecordsCount(versionId);
	}
}
