package sk.uniza.locationservice.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.repository.entity.UpdateProcessingTaskEntity;

@Repository
public interface UpdateProcessingTaskRepository extends CrudRepository<UpdateProcessingTaskEntity, Long> {

	@Modifying
	@Query("UPDATE update_processing_task " +
			"SET " +
			"status = :status, " +
			"finished_time = NOW() " +
			"WHERE finished_time IS NULL")
	void finishUpdateProcessingTasksWithFailure(@Param("status") ProcessingStatus status);

	@Query("SELECT upt.* " +
			"FROM update_processing_task upt " +
			"JOIN update u ON (u.update_id = upt.update_id)" +
			"WHERE upt.update_id = :updateId " +
			"ORDER BY upt.processing_task_id ASC ")
	List<UpdateProcessingTaskEntity> updateProcessingTasksByUpdateId(@Param("updateId") Long updateId);
}
