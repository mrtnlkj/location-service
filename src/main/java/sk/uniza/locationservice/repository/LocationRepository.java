package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.uniza.locationservice.bean.Location;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

	@Query("SELECT COUNT(l.location_id) " +
			"FROM location l ")
	public Long getLocationsCount();

	@Query("CALL insert_location_data_proc(:versionId, 0) ")
	public Long importLocationDataWithVersionAndGetInsertedRecordsCount(@Param("versionId") Long versionId);
}
