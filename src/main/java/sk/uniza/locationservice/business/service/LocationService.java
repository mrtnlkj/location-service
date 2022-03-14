package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import sk.uniza.locationservice.controller.bean.queryfilters.LocationsFilter;
import sk.uniza.locationservice.controller.bean.response.GetLocationsResponse;
import sk.uniza.locationservice.entity.Location;
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

	public Location getLocationById(Long locationId) {
		log.debug("getLocationById({})", locationId);
		return locationRepository.getLocationById(locationId);
	}

	public GetLocationsResponse getLocationsOverviewByFilter(LocationsFilter filter) {
		List<Location> locations = locationRepository.getLocationsByFilter(filter.getLocationId(),
																		   filter.getNameSk(),
																		   filter.getNameEn(),
																		   filter.getAreaFrom(),
																		   filter.getAreaTo(),
																		   filter.getType(),
																		   filter.getPostalCode(),
																		   filter.getLimit(),
																		   filter.getOffset());
		Long recordsCount = locationRepository.getLocationsCountByFilter(filter.getLocationId(),
																		 filter.getNameSk(),
																		 filter.getNameEn(),
																		 filter.getAreaFrom(),
																		 filter.getAreaTo(),
																		 filter.getType(),
																		 filter.getPostalCode());
		log.debug("getLocationsOverviewByFilter({}), records found: {} ", filter, recordsCount);
		return GetLocationsResponse.builder()
								   .records(locations)
								   .recordsCount(recordsCount)
								   .build();
	}
}
