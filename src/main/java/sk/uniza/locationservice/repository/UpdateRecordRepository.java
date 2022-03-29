package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.repository.entity.UpdateRecord;

@Repository
public interface UpdateRecordRepository extends CrudRepository<UpdateRecord, Long> {

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"ORDER BY ur.update_id DESC " +
			"LIMIT 1 ")
	UpdateRecord getLatestUpdateRecord();

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url = :url OR :url::text IS NULL) " +
			"ORDER BY ur.started_time DESC " +
			"LIMIT :limit OFFSET :offset ")
	List<UpdateRecord> getUpdateRecordsByFilter(@Param("status") UpdateStatus status,
												@Param("trigger") UpdateTrigger trigger,
												@Param("url") URL url,
												@Param("limit") Long limit,
												@Param("offset") Long offset);

	@Query("SELECT COUNT(ur.update_id) " +
			"FROM update_record ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url = :url OR :url::text IS NULL) ")
	Long getUpdateRecordsCountByFilter(@Param("status") UpdateStatus status,
									   @Param("trigger") UpdateTrigger trigger,
									   @Param("url") URL url);

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE ((EXTRACT(EPOCH FROM (NOW() - ur.started_time)) / 60) > :xMinutes) " +
			"AND (status = :status )")
	List<UpdateRecord> getUpdateRecordsWithStatusAndStartedTimeBeforeXMinutes(@Param("status") UpdateStatus status,
																			  @Param("xMinutes") Long xMinutes);

}
