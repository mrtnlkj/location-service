package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import sk.uniza.locationservice.controller.bean.queryfilters.CoordinatesFilter;
import sk.uniza.locationservice.controller.bean.queryfilters.LimitAndOffsetFilter;
import sk.uniza.locationservice.controller.bean.queryfilters.LocationsFilter;
import sk.uniza.locationservice.controller.bean.response.GetLocationsResponse;
import sk.uniza.locationservice.controller.bean.response.LocationResponse;
import sk.uniza.locationservice.mapper.LocationMapper;
import sk.uniza.locationservice.repository.LocationRepository;
import sk.uniza.locationservice.repository.entity.Location;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;
	private final LocationMapper locationMapper;

	public Long getLocationsCount() {
		return locationRepository.getLocationsCount();
	}

	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(Long versionId) {
		log.debug("importLocationDataWithVersionAndGetInsertedRecordsCount({})", versionId);
		return locationRepository.importLocationDataWithVersionAndGetInsertedRecordsCount(versionId);
	}

	public LocationResponse getLocationById(Long locationId) {
		log.debug("getLocationById({})", locationId);
		Location location = locationRepository.getLocationById(locationId);
		return locationMapper.map(location);
	}

	public GetLocationsResponse getLocationsOverviewByFilter(LocationsFilter filter) {
		log.debug("getLocationsOverviewByFilter({}) ", filter);
		List<Location> locations = locationRepository.getLocationsByFilter(filter.getLocationId(),
																		   filter.getNameSk(),
																		   filter.getNameEn(),
																		   filter.getAreaFrom(),
																		   filter.getAreaTo(),
																		   filter.getType(),
																		   filter.getPostalCode(),
																		   filter.getLimit(),
																		   filter.getOffset());
		Long count = locationRepository.getLocationsCountByFilter(filter.getLocationId(),
																  filter.getNameSk(),
																  filter.getNameEn(),
																  filter.getAreaFrom(),
																  filter.getAreaTo(),
																  filter.getType(),
																  filter.getPostalCode());
		return GetLocationsResponse.builder()
								   .records(locationMapper.map(locations))
								   .recordsCount(count)
								   .build();
	}

	public LocationResponse getNearestLocationByGpsCoords(CoordinatesFilter filter) {
		log.debug("getNearestLocationByGpsCoords({})", filter);

		Location location = locationRepository.getNearestLocationByGpsCoords(filter.getLat(),
																			 filter.getLon());
		return locationMapper.map(location);
	}

	public GetLocationsResponse getLocationsWithinSpecifiedDistance(BigDecimal distance,
																	CoordinatesFilter coordsFilter,
																	LimitAndOffsetFilter limitAndOffsetFilter) {

		log.debug("getLocationsWithinSpecifiedDistance({}, {}, {})", distance, coordsFilter, limitAndOffsetFilter);
		List<Location> locations = locationRepository.getLocationsWithinSpecifiedDistance(distance,
																						  coordsFilter.getLat(),
																						  coordsFilter.getLon(),
																						  limitAndOffsetFilter.getLimit(),
																						  limitAndOffsetFilter.getOffset());
		Long count = locationRepository.getLocationsWithinSpecifiedDistanceCount(distance,
																				 coordsFilter.getLat(),
																				 coordsFilter.getLon(),
																				 limitAndOffsetFilter.getLimit(),
																				 limitAndOffsetFilter.getOffset());
		return GetLocationsResponse.builder()
								   .records(locationMapper.map(locations))
								   .recordsCount(count)
								   .build();
	}

	public boolean gpsCoordsOccurrenceWithinLocation(Long locationId, CoordinatesFilter filter) {
		log.debug("gpsCoordsOccurrenceWithinLocation({}, {})", locationId, filter);
		return locationRepository.gpsCoordsOccurrenceWithinLocation(locationId,
																	filter.getLat(),
																	filter.getLon());
	}
}
