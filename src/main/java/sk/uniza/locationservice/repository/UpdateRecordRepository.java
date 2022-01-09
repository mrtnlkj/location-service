package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import sk.uniza.locationservice.bean.UpdateRecord;

@Repository
public interface UpdateRecordRepository extends CrudRepository<UpdateRecord, Long> {

	@Query("SELECT ur.*" +
			"FROM update_record ur " +
			"ORDER BY ur.update_id DESC " +
			"LIMIT 1 ")
	public UpdateRecord getLatestUpdateRecord();

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"ORDER BY ur.started_time DESC ")
	public List<UpdateRecord> getUpdateRecords();

}
