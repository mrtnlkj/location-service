package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
								   .records(locationMapper.map(entities))
								   .recordsCount(count)
								   .build();
	}

	public Long getLocationsCount() {
		return locationRepository.getLocationsCount();
	}

	public LocationResponse getLocationById(Long locationId) {
		log.debug("getLocationById({})", locationId);
		LocationEntity entity = locationRepository.getLocationById(locationId);
		return locationMapper.map(entity);
	}

	public LocationResponse getNearestLocationByGpsCoords(CoordinatesFilter filter) {
		log.debug("getNearestLocationByGpsCoords({})", filter);

		LocationEntity entity = locationRepository.getNearestLocationByGpsCoords(filter.getLat(),
																				 filter.getLon());
		return locationMapper.map(entity);
	}

	public GetLocationsResponse getLocationsWithinSpecifiedDistance(BigDecimal distance,
																	CoordinatesFilter coordsFilter,
																	LimitAndOffsetFilter limitAndOffsetFilter) {

		log.debug("getLocationsWithinSpecifiedDistance({}, {}, {})", distance, coordsFilter, limitAndOffsetFilter);
		List<LocationEntity> entities = locationRepository.getLocationsWithinSpecifiedDistance(distance,
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

	@Transactional
	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(Long versionId) {
		log.debug("importLocationDataWithVersionAndGetInsertedRecordsCount({})", versionId);
		return locationRepository.callInsertLocationDataProc(versionId);
	}

	@Transactional
	public void processRegionNames(Long versionId) {
		locationRepository.callProcessRegionNamesProc(versionId);
	}

	@Transactional
	public void processDistrictNames(Long versionId) {
		locationRepository.callProcessDistrictNamesProc(versionId);
	}

	@Transactional
	public void processStateNames(Long versionId) {
		locationRepository.callProcessStateNamesProc(versionId);
	}
}
