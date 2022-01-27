package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import sk.uniza.locationservice.bean.Location;
import sk.uniza.locationservice.bean.enums.LocationType;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l ")
	public Long getLocationsCount();

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version)) " +
			"WHERE (l.location_id = :locationId OR :locationId IS NULL) " +
			"AND (UPPER(l.name_sk) ILIKE UPPER(:nameSk) OR :nameSk IS NULL) " +
			"AND (UPPER(l.name_en) ILIKE UPPER(:nameEn) OR :nameEn IS NULL) " +
			"AND (" +
			" ((l.area >= :areaFrom AND :areaTo IS NULL) OR :areaFrom IS NULL) " +
			" OR ((l.area <= :areaTo AND :areaFrom IS NULL) OR :areaTo IS NULL) " +
			" OR ((l.area >= :areaFrom AND l.area <= :areaTo) OR (:areaFrom IS NULL AND :areaTo IS NULL)) " +
			") " +
			"AND (UPPER(l.type) = UPPER(:type) OR :type IS NULL) " +
			"AND (REPLACE(l.postal_code, ' ', '') ILIKE REPLACE(:postalCode, ' ', '') OR :postalCode IS NULL) ")
	public Long getLocationsCountByFilter(@Param("locationId") Long locationId,
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
			"JOIN location_version lv ON (lv.version_id = l.version_id) AND (l.version_id IN (SELECT MAX(version_id) FROM location_version)) " +
			"WHERE (l.location_id = :locationId OR :locationId IS NULL) " +
			"AND (UPPER(l.name_sk) ILIKE UPPER(:nameSk) OR :nameSk IS NULL) " +
			"AND (UPPER(l.name_en) ILIKE UPPER(:nameEn) OR :nameEn IS NULL) " +
			"AND (" +
			" ((l.area >= :areaFrom AND :areaTo IS NULL) OR :areaFrom IS NULL) " +
			" OR ((l.area <= :areaTo AND :areaFrom IS NULL) OR :areaTo IS NULL) " +
			" OR ((l.area >= :areaFrom AND l.area <= :areaTo) OR (:areaFrom IS NULL AND :areaTo IS NULL)) " +
			") " +
			"AND (UPPER(l.type) = UPPER(:type) OR :type IS NULL) " +
			"AND (REPLACE(l.postal_code, ' ', '') ILIKE REPLACE(:postalCode, ' ', '') OR :postalCode IS NULL) " +
			"ORDER BY l.location_id ASC " +
			"LIMIT :limit OFFSET :offset")
	public List<Location> getLocationsByFilter(@Param("locationId") Long locationId,
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
			"public.ST_AsGeoJSON(l.boundary) AS boundary " +
			"FROM location l " +
			"JOIN location_version lv ON (lv.version_id = l.version_id) " +
			"WHERE (l.location_id = :locationId) ")
	public Location getLocationById(@Param("locationId") Long locationId);

	@Query("CALL insert_location_data_proc(:versionId, 0) ")
	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(@Param("versionId") Long versionId);
}
