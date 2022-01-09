package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import sk.uniza.locationservice.bean.RunUpdateRequest;
import sk.uniza.locationservice.bean.UpdateRecord;
import sk.uniza.locationservice.bean.UpdateWrapper;
import sk.uniza.locationservice.bean.enums.UpdateTrigger;
import sk.uniza.locationservice.config.DataUpdaterProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataUpdateRunner {

	private final DataUpdaterProperties dataUpoDataUpdaterProperties;
	private final DataUpdater dataUpdater;
	private final UpdateRecordService updateRecordService;

	public UpdateRecord manualUpdate(RunUpdateRequest request) {
		final UpdateTrigger trigger = UpdateTrigger.MANUAL_UPDATE;
		log.debug("Data {} STARTED, request: {}", trigger, request);
		if (dataUpdater.isUpdateRunning()) {
			//TODO exception
			throw new RuntimeException("data is alreadyUpdating");
		}
		UpdateWrapper wrapper = UpdateWrapper.builder().fromRunUpdateRequest(request,
																			 dataUpoDataUpdaterProperties.getFileDownloader().getDownloadUrl())
											 .trigger(trigger)
											 .build();
		UpdateRecord update = updateRecordService.saveRunningUpdate(wrapper);
		dataUpdater.update(wrapper);
		return update;
	}

}
