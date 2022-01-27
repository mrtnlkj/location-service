package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;

@Repository
public interface UpdateRecordRepository extends CrudRepository<UpdateRecord, Long> {

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"ORDER BY ur.update_id DESC " +
			"LIMIT 1 ")
	public UpdateRecord getLatestUpdateRecord();

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url = :url OR :url IS NULL) " +
			"ORDER BY ur.started_time DESC ")
	public List<UpdateRecord> getUpdateRecords(@Param("status") UpdateStatus status,
											   @Param("trigger") UpdateTrigger trigger,
											   @Param("url") URL url);

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE ((EXTRACT(EPOCH FROM (NOW() - ur.started_time)) / 60) > :xMinutes) " +
			"AND (status = :status )")
	public List<UpdateRecord> getUpdateRecordsWithStatusAndStartedTimeBeforeXMinutes(@Param("status") UpdateStatus status,
																					 @Param("xMinutes") Long xMinutes);
}
