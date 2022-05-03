package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import sk.uniza.locationservice.business.update.UpdateExecutor;
import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;
import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.ProcessingStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateType;
import sk.uniza.locationservice.controller.bean.queryfilters.UpdateRecordsFilter;
import sk.uniza.locationservice.controller.bean.request.ManualUpdateRequest;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapper;
import sk.uniza.locationservice.controller.bean.response.GetUpdateRecordsResponse;
import sk.uniza.locationservice.controller.bean.response.SuccessResponse;
import sk.uniza.locationservice.controller.bean.response.UpdateResponse;
import sk.uniza.locationservice.mapper.UpdateMapper;
import sk.uniza.locationservice.repository.UpdateRepository;
import sk.uniza.locationservice.repository.entity.UpdateEntity;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateService {

	private final UpdateRepository updateRepository;
	private final UpdateMapper updateMapper;
	private final UpdateExecutor updateExecutor;
	private final UpdateProperties updateProperties;
	private final UpdateProcessingTaskService updateProcessingTaskService;

	public UpdateResponse getLatestUpdateRecord() {
		UpdateEntity entity = updateRepository.getLatestUpdate();
		if (entity != null) {
			entity.setTasks(updateProcessingTaskService.getTasksByUpdateId(entity.getUpdateId()));
		}
		return updateMapper.map(entity);
	}

	public UpdateResponse executeManualUpdate(ManualUpdateRequest request) {
		final UpdateType type = UpdateType.MANUAL;

		if (updateExecutor.isInProgress()) {
			log.debug("Cannot execute manual update, update is already in progress.");
			throw new LocationServiceException(ErrorType.UPDATE_IN_PROGRESS);
		}
		UpdateWrapper wrapper = wrapUpdateRequest(type, request);
		UpdateEntity entity = saveRunningUpdate(wrapper);
		wrapper.setUpdate(entity);

		updateExecutor.executeUpdate(wrapper);

		return updateMapper.map(entity);
	}

	public SuccessResponse abortUpdate(Long updateId) {
		if (updateExecutor.isInProgress()) {
			UpdateEntity entity = updateRepository.getUpdateByIdAndStatus(updateId, ProcessingStatus.RUNNING);
			if (entity != null) {
				log.debug("Aborting running update with id: {}.", entity.getUpdateId());
				updateExecutor.abortManually();
				return SuccessResponse.builder()
									  .value(Boolean.TRUE)
									  .build();
			}
		}
		throw new LocationServiceException(ErrorType.NO_UPDATE_TO_ABORT);
	}

	public GetUpdateRecordsResponse getUpdateRecords(UpdateRecordsFilter filter) {
		List<UpdateEntity> updates = updateRepository.getUpdateRecordsByFilter(filter.getStatus(),
																			   filter.getType(),
																			   filter.getUrl(),
																			   filter.getDateStartedFrom(),
																			   filter.getLimit(),
																			   filter.getOffset());
		Long count = updateRepository.getUpdateRecordsCountByFilter(filter.getStatus(),
																	filter.getType(),
																	filter.getUrl(),
																	filter.getDateStartedFrom());

		return GetUpdateRecordsResponse.builder()
									   .records(updateMapper.map(updates))
									   .recordsCount(count)
									   .build();
	}

	public UpdateEntity save(UpdateEntity update) {
		log.debug("save({})", update);
		return updateRepository.save(update);
	}

	public UpdateEntity saveRunningUpdate(UpdateWrapper wrapper) {
		log.debug("saveRunningUpdate({})", wrapper);

		UpdateEntity update = UpdateEntity.builder()
										  .buildRunningUpdate(wrapper.getUrl().toString(),
															  wrapper.getType(),
															  wrapper.getDescription())
										  .build();
		return this.save(update);
	}

	public UpdateEntity getOrCreateRunningUpdateRecord(UpdateWrapper wrapper) {
		if (UpdateType.MANUAL != wrapper.getType()) {
			return this.saveRunningUpdate(wrapper);
		}
		UpdateEntity update = updateRepository.getLatestUpdate();
		return nonNull(update)
					   && ProcessingStatus.RUNNING.equals(update.getStatus()) ? update
																			  : this.saveRunningUpdate(wrapper);
	}

	public UpdateEntity getLatestUpdateEntity() {
		log.debug("getLatestUpdateEntity()");
		return updateRepository.getLatestUpdate();
	}

	public UpdateResponse getUpdateRecordById(Long updateId) {
		log.debug("getUpdateById({})", updateId);
		UpdateEntity entity = updateRepository.getUpdateById(updateId);
		if (entity != null) {
			entity.setTasks(updateProcessingTaskService.getTasksByUpdateId(entity.getUpdateId()));
		}
		return updateMapper.map(entity);
	}

	public void finishUpdatesWithFailure() {
		final String failedReason = "Force kill stuck update - running from previous application run";
		updateRepository.finishUpdatesWithFailure(failedReason, ProcessingStatus.FAILED);
	}

	private UpdateWrapper wrapUpdateRequest(UpdateType type, ManualUpdateRequest request) {
		return UpdateWrapper.builder().fromRunUpdateRequest(request, updateProperties.getFileDownloader().getDownloadUrl())
							.type(type)
							.build();
	}
}
