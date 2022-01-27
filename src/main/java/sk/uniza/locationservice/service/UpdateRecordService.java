package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import sk.uniza.locationservice.bean.OverviewResponse;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateRecordsFilter;
import sk.uniza.locationservice.bean.UpdateWrapperRequest;
import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.repository.UpdateRecordRepository;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRecordService implements UpdateRecordMarker {

	private final UpdateRecordRepository updateRecordRepository;

	@Override
	public UpdateRecord getLatestUpdateRecord() {
		return updateRecordRepository.getLatestUpdateRecord();
	}

	public OverviewResponse<UpdateRecord> getUpdateRecordsByFilter(UpdateRecordsFilter filter) {
		List<UpdateRecord> records = updateRecordRepository.getUpdateRecordsByFilter(filter.getStatus(),
																					 filter.getTrigger(),
																					 filter.getUrl(),
																					 filter.getLimit(),
																					 filter.getOffset());
		Long recordsCount = updateRecordRepository.getUpdateRecordsCountByFilter(filter.getStatus(),
																				 filter.getTrigger(),
																				 filter.getUrl());

		return OverviewResponse.<UpdateRecord>builder()
							   .records(records)
							   .recordsCount(recordsCount)
							   .build();
	}

	public UpdateRecord save(UpdateRecord update) {
		log.debug("save({})", update);
		return updateRecordRepository.save(update);
	}

	public UpdateRecord saveRunningUpdate(UpdateWrapperRequest wrapper) {
		log.debug("saveRunningUpdate({})", wrapper);

		UpdateRecord update = UpdateRecord.builder()
										  .buildRunningUpdate(wrapper.getUrl(),
															  wrapper.getTrigger(),
															  wrapper.getDescription())
										  .build();
		return this.save(update);
	}

	@Override
	public UpdateRecord getOrCreateRunningUpdateRecord(UpdateWrapperRequest wrapper) {
		if (UpdateTrigger.MANUAL_UPDATE != wrapper.getTrigger()) {
			return this.saveRunningUpdate(wrapper);
		}
		UpdateRecord update = this.getLatestUpdateRecord();
		return nonNull(update)
					   && UpdateStatus.RUNNING.equals(update.getStatus()) ? update
																		  : this.saveRunningUpdate(wrapper);
	}

	@Override
	public void markUpdateRecordAs(UpdateRecord update, UpdateStatus status) {
		log.debug("markUpdateRecordAs({}, {})", update, status);
		if (nonNull(update)) {
			update = update.toBuilder().markUpdateAs(status).build();
			this.save(update);
		}
	}

	@Scheduled(fixedDelayString = "PT1H")
	public void markRunningUpdatesAsFailedAfterXMinutes() {
		final long xMinutes = 120L;
		List<UpdateRecord> recordList = updateRecordRepository.getUpdateRecordsWithStatusAndStartedTimeBeforeXMinutes(UpdateStatus.RUNNING,
																													  xMinutes);
		recordList.forEach(r -> {
			r.toBuilder().markUpdateAs(UpdateStatus.FAILED);
			updateRecordRepository.save(r);
		});
	}
}
