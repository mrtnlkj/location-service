package sk.uniza.locationservice.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import sk.uniza.locationservice.config.properties.UpdateProperties;
import sk.uniza.locationservice.controller.bean.enums.UpdateStatus;
import sk.uniza.locationservice.controller.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.controller.bean.queryfilters.UpdateRecordsFilter;
import sk.uniza.locationservice.controller.bean.request.UpdateWrapperRequest;
import sk.uniza.locationservice.controller.bean.response.GetUpdateRecordsResponse;
import sk.uniza.locationservice.controller.bean.response.UpdateRecordResponse;
import sk.uniza.locationservice.mapper.UpdateRecordMapper;
import sk.uniza.locationservice.repository.UpdateRecordRepository;
import sk.uniza.locationservice.repository.entity.UpdateRecordEntity;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRecordService {

	private final UpdateRecordRepository updateRepository;
	private final UpdateRecordMapper updateRecordMapper;
	private final UpdateProperties updateProperties;

	public UpdateRecordResponse getLatestUpdateRecord() {
		UpdateRecordEntity update = updateRepository.getLatestUpdateRecord();
		return updateRecordMapper.map(update);
	}

	public GetUpdateRecordsResponse getUpdateRecords(UpdateRecordsFilter filter) {
		List<UpdateRecordEntity> updates = updateRepository.getUpdateRecordsByFilter(filter.getStatus(),
																					 filter.getTrigger(),
																					 filter.getUrl(),
																					 filter.getDateStartedFrom(),
																					 filter.getLimit(),
																					 filter.getOffset());
		Long count = updateRepository.getUpdateRecordsCountByFilter(filter.getStatus(),
																	filter.getTrigger(),
																	filter.getUrl(),
																	filter.getDateStartedFrom());

		return GetUpdateRecordsResponse.builder()
									   .records(updateRecordMapper.map(updates))
									   .recordsCount(count)
									   .build();
	}

	public UpdateRecordEntity save(UpdateRecordEntity update) {
		log.debug("save({})", update);
		return updateRepository.save(update);
	}

	public UpdateRecordEntity saveRunningUpdate(UpdateWrapperRequest wrapper) {
		log.debug("saveRunningUpdate({})", wrapper);

		UpdateRecordEntity update = UpdateRecordEntity.builder()
													  .buildRunningUpdate(wrapper.getUrl(),
																		  wrapper.getTrigger(),
																		  wrapper.getDescription())
													  .build();
		return this.save(update);
	}

	public UpdateRecordEntity getOrCreateRunningUpdateRecord(UpdateWrapperRequest wrapper) {
		if (UpdateTrigger.MANUAL_UPDATE != wrapper.getTrigger()) {
			return this.saveRunningUpdate(wrapper);
		}
		UpdateRecordEntity update = updateRepository.getLatestUpdateRecord();
		return nonNull(update)
					   && UpdateStatus.RUNNING.equals(update.getStatus()) ? update
																		  : this.saveRunningUpdate(wrapper);
	}

	public void markUpdateRecordAs(UpdateRecordEntity update, UpdateStatus status, String failedReason) {
		log.debug("markUpdateRecordAs({}, {})", update, status);
		if (nonNull(update)) {
			update = update.toBuilder()
						   .markUpdateAs(status)
						   .failedReason(failedReason)
						   .build();
			this.save(update);
		}
	}

	public UpdateRecordEntity getLatestUpdate() {
		log.debug("getLatestUpdate()");
		return updateRepository.getLatestUpdateRecord();
	}

	public UpdateRecordResponse getUpdateRecordById(Long updateId) {
		log.debug("getUpdateById({})", updateId);
		UpdateRecordEntity update = updateRepository.getUpdateById(updateId);
		return updateRecordMapper.map(update);
	}

	@Scheduled(fixedDelayString = "PT1M")
	public void markUpdatesAsFailedAfterXMinutes() {
		long maxLivingUpdateMinutes = updateProperties.getMaxLivingDuration().toMinutes();
		final String failedReason = "Force kill stuck update - running more than " + maxLivingUpdateMinutes + " minutes.";
		updateRepository.killStuckUpdatesAndSetFailedReason(maxLivingUpdateMinutes, failedReason, UpdateStatus.FAILED);
	}
}
