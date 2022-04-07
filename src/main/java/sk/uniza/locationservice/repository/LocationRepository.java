package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.LocationType;
import sk.uniza.locationservice.repository.entity.LocationEntity;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l ")
	Long getLocationsCount();

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE ((l.location_id = :locationId) OR :locationId IS NULL) " +
			"AND ((UNACCENT_STR(l.name_sk) ILIKE UNACCENT_STR(:nameSk)) OR :nameSk IS NULL) " +
			"AND ((UNACCENT_STR(l.name_en) ILIKE UNACCENT_STR(:nameEn)) OR :nameEn IS NULL) " +
			"AND (" +
			" (((l.area >= :areaFrom) OR :areaFrom IS NULL) " +
			" AND ((l.area <= :areaTo) OR :areaTo IS NULL)) " +
			" OR (l.area BETWEEN :areaFrom AND :areaTo) " +
			") " +
			"AND ((l.type = :type) OR :type IS NULL) " +
			"AND ((REPLACE(l.postal_code, ' ', '') ILIKE REPLACE(:postalCode, ' ', '')) OR :postalCode IS NULL) ")
	Long getLocationsCountByFilter(@Param("locationId") Long locationId,
								   @Param("nameSk") String nameSk,
								   @Param("nameEn") String nameEn,
								   @Param("areaFrom") BigDecimal areaFrom,
								   @Param("areaTo") BigDecimal areaTo,
								   @Param("type") LocationType type,
								   @Param("postalCode") String postalCode);

	@Query("SELECT " +
			"l.location_id AS location_id, " +
			"l.version_id AS version_id, " +
			"l.name_sk AS name_sk, " +
			"l.name_en AS name_en, " +
			"l.area AS area, " +
			"l.population AS population, " +
			"l.district_name_sk AS district_name_sk, " +
			"l.district_name_en AS district_name_en, " +
			"l.region_name_sk AS region_name_sk, " +
			"l.region_name_en AS region_name_en, " +
			"l.state_name_sk AS state_name_sk, " +
			"l.state_name_en AS state_name_en, " +
			"l.is_in AS is_in, " +
			"l.postal_code AS postal_code, " +
			"l.type AS type, " +
			"l.lat AS lat, " +
			"l.lon AS lon, " +
			"NULL AS boundary " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE ((l.location_id = :locationId) OR :locationId IS NULL) " +
			"AND ((UNACCENT_STR(l.name_sk) ILIKE UNACCENT_STR(:nameSk)) OR :nameSk IS NULL) " +
			"AND ((UNACCENT_STR(l.name_en) ILIKE UNACCENT_STR(:nameEn)) OR :nameEn IS NULL) " +
			"AND (" +
			" (((l.area >= :areaFrom) OR :areaFrom IS NULL) " +
			" AND ((l.area <= :areaTo) OR :areaTo IS NULL)) " +
			" OR (l.area BETWEEN :areaFrom AND :areaTo) " +
			") " +
			"AND ((l.type = :type) OR :type IS NULL) " +
			"AND ((REPLACE(l.postal_code, ' ', '') ILIKE REPLACE(:postalCode, ' ', '')) OR :postalCode IS NULL) " +
			"ORDER BY l.location_id ASC " +
			"LIMIT :limit OFFSET :offset")
	List<LocationEntity> getLocationsByFilter(@Param("locationId") Long locationId,
											  @Param("nameSk") String nameSk,
											  @Param("nameEn") String nameEn,
											  @Param("areaFrom") BigDecimal areaFrom,
											  @Param("areaTo") BigDecimal areaTo,
											  @Param("type") LocationType type,
											  @Param("postalCode") String postalCode,
											  @Param("limit") Long limit,
											  @Param("offset") Long offset);

	@Query("SELECT " +
			"l.location_id AS location_id, " +
			"l.version_id AS version_id, " +
			"l.name_sk AS name_sk, " +
			"l.name_en AS name_en, " +
			"l.area AS area, " +
			"l.population AS population, " +
			"l.district_name_sk AS district_name_sk, " +
			"l.district_name_en AS district_name_en, " +
			"l.region_name_sk AS region_name_sk, " +
			"l.region_name_en AS region_name_en, " +
			"l.state_name_sk AS state_name_sk, " +
			"l.state_name_en AS state_name_en, " +
			"l.is_in AS is_in, " +
			"l.postal_code AS postal_code, " +
			"l.type AS type, " +
			"l.lat AS lat, " +
			"l.lon AS lon, " +
			"public.ST_AsGeoJSON(public.ST_Transform(l.boundary,4326)) AS boundary " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE (l.location_id = :locationId) ")
	LocationEntity getLocationById(@Param("locationId") Long locationId);

	@Query("SELECT " +
			"l.location_id AS location_id, " +
			"l.version_id AS version_id, " +
			"l.name_sk AS name_sk, " +
			"l.name_en AS name_en, " +
			"l.area AS area, " +
			"l.population AS population, " +
			"l.district_name_sk AS district_name_sk, " +
			"l.district_name_en AS district_name_en, " +
			"l.region_name_sk AS region_name_sk, " +
			"l.region_name_en AS region_name_en, " +
			"l.state_name_sk AS state_name_sk, " +
			"l.state_name_en AS state_name_en, " +
			"l.is_in AS is_in, " +
			"l.postal_code AS postal_code, " +
			"l.type AS type, " +
			"l.lat AS lat, " +
			"l.lon AS lon, " +
			"public.ST_AsGeoJSON(public.ST_Transform(l.boundary,4326)) AS boundary, " +
			"public.ST_Distance( " +
			"public.ST_Transform(public.ST_SetSRID(public.ST_MakePoint(:lon, :lat), 4326), 4326), " +
			"public.ST_Transform(l.boundary, 4326)" +
			") AS distance " +
			"FROM location l " +
			"JOIN location_version lv " +
			"ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"ORDER BY distance ASC " +
			"LIMIT 1 ")
	LocationEntity getNearestLocationByGpsCoords(@Param("lat") BigDecimal lat,
												 @Param("lon") BigDecimal lon);

	@Query("SELECT " +
			"l.location_id AS location_id, " +
			"l.version_id AS version_id, " +
			"l.name_sk AS name_sk, " +
			"l.name_en AS name_en, " +
			"l.area AS area, " +
			"l.population AS population, " +
			"l.district_name_sk AS district_name_sk, " +
			"l.district_name_en AS district_name_en, " +
			"l.region_name_sk AS region_name_sk, " +
			"l.region_name_en AS region_name_en, " +
			"l.state_name_sk AS state_name_sk, " +
			"l.state_name_en AS state_name_en, " +
			"l.is_in AS is_in, " +
			"l.postal_code AS postal_code, " +
			"l.type AS type, " +
			"l.lat AS lat, " +
			"l.lon AS lon, " +
			"NULL AS boundary, " +
			"public.ST_Distance( " +
			"public.ST_Transform(public.ST_SetSRID(public.ST_MakePoint(:lon, :lat), 4326), 4326), " +
			"public.ST_Transform(l.boundary, 4326) " +
			") AS distance " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE " +
			"public.ST_Distance( " +
			"public.ST_Transform(public.ST_SetSRID(public.ST_MakePoint(:lon, :lat), 4326), 4326), " +
			"public.ST_Transform(l.boundary, 4326) " +
			") <= :distance " +
			"ORDER BY distance ASC " +
			"LIMIT :limit OFFSET :offset ")
	List<LocationEntity> getLocationsWithinSpecifiedDistance(@Param("distance") BigDecimal distance,
															 @Param("lat") BigDecimal lat,
															 @Param("lon") BigDecimal lon,
															 @Param("limit") Long limit,
															 @Param("offset") Long offset);

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) " +
			"AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE " +
			"public.ST_Distance( " +
			"public.ST_Transform(public.ST_SetSRID(public.ST_MakePoint(:lon, :lat), 4326), 4326), " +
			"public.ST_Transform(l.boundary, 4326) " +
			") <= :distance ")
	Long getLocationsWithinSpecifiedDistanceCount(@Param("distance") BigDecimal distance,
												  @Param("lat") BigDecimal lat,
												  @Param("lon") BigDecimal lon,
												  @Param("limit") Long limit,
												  @Param("offset") Long offset);

	@Query("SELECT COUNT(l.location_id)" +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version WHERE validity_from IS NOT NULL)) " +
			"WHERE " +
			"l.location_id = :locationId " +
			"AND public.ST_Intersects(public.ST_SetSRID(public.ST_MakePoint(:lon, :lat), 4326), public.ST_Transform(l.boundary, 4326)) ")
	Long getGpsCoordsOccurrenceWithinLocationCount(@Param("locationId") Long locationId,
												   @Param("lat") BigDecimal lat,
												   @Param("lon") BigDecimal lon);

	@Query("CALL insert_location_data_proc(:versionId, 0) ")
	Long callInsertLocationDataProc(@Param("versionId") Long versionId);

	@Query("CALL process_state_names_proc(:versionId, 0) ")
	Long callProcessStateNamesProc(@Param("versionId") Long versionId);

	@Query("CALL process_region_names_proc(:versionId, 0) ")
	Long callProcessRegionNamesProc(@Param("versionId") Long versionId);

	@Query("CALL process_district_names_proc(:versionId, 0) ")
	Long callProcessDistrictNamesProc(@Param("versionId") Long versionId);
}
