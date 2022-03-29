package sk.uniza.locationservice.business.update;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import sk.uniza.locationservice.controller.bean.request.RunUpdateRequest;
import sk.uniza.locationservice.repository.entity.UpdateRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManualUpdateTriggerService {

	private final Optional<ManualUpdateExecutor> manualUpdateExecutor;

	public UpdateRecord triggerUpdate(RunUpdateRequest request) {
		log.debug("triggerUpdate({})", request);
		if (!manualUpdateExecutor.isPresent()) {
			//TODO exception, that manual update is disabled
			throw new RuntimeException("blablabla");
		}
		return manualUpdateExecutor.get().triggerUpdate(request);
	}
}
