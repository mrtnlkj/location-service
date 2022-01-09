package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapper;
import sk.uniza.locationservice.bean.enums.UpdateStatus;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.repository.UpdateRecordRepository;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateRecordService implements UpdateRecordMarker {

	private final UpdateRecordRepository updateRecordRepository;

	public UpdateRecord save(UpdateRecord update) {
		log.debug("save({})", update);
		return updateRecordRepository.save(update);
	}

	public UpdateRecord getLatestUpdateRecord() {
		return updateRecordRepository.getLatestUpdateRecord();
	}

	public UpdateRecord saveRunningUpdate(UpdateWrapper wrapper) {
		log.debug("saveRunningUpdate({})", wrapper);

		UpdateRecord update = UpdateRecord.builder()
										  .buildRunningUpdate(wrapper.getUrl(),
															  wrapper.getTrigger(),
															  wrapper.getDescription())
										  .build();
		return this.save(update);
	}

	@Override
	public UpdateRecord getOrCreateRunningUpdateRecord(UpdateWrapper wrapper) {
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
}
