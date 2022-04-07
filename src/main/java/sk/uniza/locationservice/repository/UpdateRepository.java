package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;
import sk.uniza.locationservice.repository.entity.UpdateEntity;

@Repository
public interface UpdateRepository extends CrudRepository<UpdateEntity, Long> {

	@Query("SELECT ur.* " +
			"FROM update ur " +
			"ORDER BY ur.update_id DESC " +
			"LIMIT 1 ")
	UpdateEntity getLatestUpdate();

	@Query("SELECT ur.* " +
			"FROM update ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url ILIKE :url OR :url IS NULL) " +
			"AND (ur.started_time <= :dateStartedFrom OR :dateStartedFrom::timestamptz IS NULL) " +
			"ORDER BY ur.started_time DESC " +
			"LIMIT :limit OFFSET :offset ")
	List<UpdateEntity> getUpdateRecordsByFilter(@Param("status") ProcessingStatus status,
												@Param("trigger") UpdateType type,
												@Param("url") String url,
												@Param("dateStartedFrom") Instant dateStartedFrom,
												@Param("limit") Long limit,
												@Param("offset") Long offset);

	@Query("SELECT COUNT(ur.update_id) " +
			"FROM update ur " +
			"WHERE (ur.status = :status OR :status IS NULL) " +
			"AND (ur.trigger = :trigger OR :trigger IS NULL) " +
			"AND (ur.data_download_url ILIKE :url OR :url IS NULL) " +
			"AND (ur.started_time <= :dateStartedFrom OR :dateStartedFrom::timestamptz IS NULL) ")
	Long getUpdateRecordsCountByFilter(@Param("status") ProcessingStatus status,
									   @Param("trigger") UpdateType type,
									   @Param("url") String url,
									   @Param("dateStartedFrom") Instant dateStartedFrom);

	@Query("SELECT ur.* " +
			"FROM update ur " +
			"WHERE (ur.update_id = :updateId) ")
	UpdateEntity getUpdateById(@Param("updateId") Long updateId);

	@Query("SELECT ur.* " +
			"FROM update ur " +
			"WHERE (ur.update_id = :updateId) " +
			"AND (ur.status = :status)")
	UpdateEntity getUpdateByIdAndStatus(@Param("updateId") Long updateId,
										@Param("status") ProcessingStatus status);

	@Modifying
	@Query("UPDATE update " +
			"SET " +
			"failed_reason = :failedReason, " +
			"status = :status, " +
			"finished_time = NOW() " +
			"WHERE finished_time IS NULL")
	void finishUpdatesWithFailure(@Param("failedReason") String failedReason,
								  @Param("status") ProcessingStatus status);

}
