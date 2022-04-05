package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

@Repository
public interface UpdateRecordRepository extends CrudRepository<UpdateRecordEntity, Long> {

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"ORDER BY ur.update_id DESC " +
			"LIMIT 1 ")
	UpdateRecordEntity getLatestUpdateRecord();

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url ILIKE :url OR :url IS NULL) " +
			"AND (ur.started_time <= :dateStartedFrom OR :dateStartedFrom::timestamptz IS NULL) " +
			"ORDER BY ur.started_time DESC " +
			"LIMIT :limit OFFSET :offset ")
	List<UpdateRecordEntity> getUpdateRecordsByFilter(@Param("status") UpdateStatus status,
													  @Param("trigger") UpdateTrigger trigger,
													  @Param("url") String url,
													  @Param("dateStartedFrom") Instant dateStartedFrom,
													  @Param("limit") Long limit,
													  @Param("offset") Long offset);

	@Query("SELECT COUNT(ur.update_id) " +
			"FROM update_record ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url ILIKE :url OR :url IS NULL) " +
			"AND (ur.started_time <= :dateStartedFrom OR :dateStartedFrom::timestamptz IS NULL) ")
	Long getUpdateRecordsCountByFilter(@Param("status") UpdateStatus status,
									   @Param("trigger") UpdateTrigger trigger,
									   @Param("url") String url,
									   @Param("dateStartedFrom") Instant dateStartedFrom);

	@Query("SELECT ur.* " +
			"FROM update_record ur " +
			"WHERE (ur.update_id = :updateId) ")
	UpdateRecordEntity getUpdateById(@Param("updateId") Long updateId);

	@Modifying
	@Query("UPDATE update_record " +
			"SET " +
			"failed_reason = :failedReason, " +
			"status = :status, " +
			"finished_time = NOW() " +
			"WHERE ((EXTRACT(EPOCH FROM (NOW() - started_time)) / 60) >= :xMinutes) " +
			"AND finished_time IS NULL")
	void killStuckUpdatesAndSetFailedReason(@Param("xMinutes") Long xMinutes,
											@Param("failedReason") String failedReason,
											@Param("status") UpdateStatus status);

}
