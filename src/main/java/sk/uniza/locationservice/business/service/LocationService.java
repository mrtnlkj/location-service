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
import sk.uniza.locationservice.controller.bean.response.SuccessResponse;
import sk.uniza.locationservice.mapper.LocationMapper;
import sk.uniza.locationservice.repository.LocationRepository;
import sk.uniza.locationservice.repository.entity.LocationEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {

	private final LocationRepository locationRepository;
	private final LocationMapper locationMapper;

	public GetLocationsResponse getLocationsOverviewByFilter(LocationsFilter filter) {
		log.debug("getLocationsOverviewByFilter({}) ", filter);
		List<LocationEntity> entities = locationRepository.getLocationsByFilter(filter.getLocationId(),
																				filter.getNameSk(),
																				filter.getNameEn(),
																				filter.getAreaFrom(),
																				filter.getAreaTo(),
																				filter.getType(),
																				filter.getPostalCode(),
																				filter.getLat(),
																				filter.getLon(),
																				filter.getEmbedGeoJson(),
																				filter.getLimit(),
																				filter.getOffset());
		Long count = locationRepository.getLocationsCountByFilter(filter.getLocationId(),
																  filter.getNameSk(),
																  filter.getNameEn(),
																  filter.getAreaFrom(),
																  filter.getAreaTo(),
																  filter.getType(),
																  filter.getPostalCode(),
																  filter.getLat(),
																  filter.getLon());
		return GetLocationsResponse.builder()
								   .records(locationMapper.map(entities))
								   .recordsCount(count)
								   .build();
	}

	public Long getLocationsCount() {
		return locationRepository.getLocationsCount();
	}

	public LocationResponse getLocationById(Long locationId, Boolean embedGeoJson) {
		log.debug("getLocationById({})", locationId);
		LocationEntity entity = locationRepository.getLocationById(locationId, embedGeoJson);
		return locationMapper.map(entity);
	}

	public LocationResponse getNearestLocationByGpsCoords(CoordinatesFilter filter, Boolean embedGeoJson) {
		log.debug("getNearestLocationByGpsCoords({})", filter);

		LocationEntity entity = locationRepository.getNearestLocationByGpsCoords(filter.getLat(),
																				 filter.getLon(),
																				 embedGeoJson);
		return locationMapper.map(entity);
	}

	public GetLocationsResponse getLocationsWithinSpecifiedDistance(BigDecimal distance,
																	CoordinatesFilter coordsFilter,
																	LimitAndOffsetFilter limitAndOffsetFilter,
																	Boolean embedGeoJson) {

		log.debug("getLocationsWithinSpecifiedDistance({}, {}, {})", distance, coordsFilter, limitAndOffsetFilter);
		List<LocationEntity> entities = locationRepository.getLocationsWithinSpecifiedDistance(distance,
																							   coordsFilter.getLat(),
																							   coordsFilter.getLon(),
																							   embedGeoJson,
																							   limitAndOffsetFilter.getLimit(),
																							   limitAndOffsetFilter.getOffset());
		Long count = locationRepository.getLocationsWithinSpecifiedDistanceCount(distance,
																				 coordsFilter.getLat(),
																				 coordsFilter.getLon(),
																				 limitAndOffsetFilter.getLimit(),
																				 limitAndOffsetFilter.getOffset());
		return GetLocationsResponse.builder()
								   .records(locationMapper.map(entities))
								   .recordsCount(count)
								   .build();
	}

	public SuccessResponse gpsCoordsOccurrenceWithinLocation(Long locationId, CoordinatesFilter filter) {
		log.debug("gpsCoordsOccurrenceWithinLocation({}, {})", locationId, filter);
		Long count = locationRepository.getGpsCoordsOccurrenceWithinLocationCount(locationId,
																				  filter.getLat(),
																				  filter.getLon());
		return SuccessResponse.builder().value(count.compareTo(0L) > 0).build();
	}
}
