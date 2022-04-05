package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.uniza.locationservice.repository.entity.LocationVersionEntity;

@Repository
public interface LocationVersionRepository extends CrudRepository<LocationVersionEntity, Long> {

	@Query("SELECT lv.* " +
			"FROM location_version lv " +
			"WHERE lv.validity_from IS NOT NULL " +
			"AND lv.validity_to IS NULL " +
			"ORDER BY lv.version_id DESC " +
			"LIMIT 1 ")
	LocationVersionEntity getLatestValidLocationVersion();
}
