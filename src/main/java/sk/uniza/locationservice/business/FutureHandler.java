package sk.uniza.locationservice.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import sk.uniza.locationservice.config.properties.UpdateProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class FutureHandler {

	private final UpdateProperties updateProperties;
	private ScheduledExecutorService scheduledExecutorService;
	private ScheduledFuture<?> timeoutFuture;
	private Future<?> future;

	private synchronized ScheduledExecutorService getScheduledExecutorService() {
		if (scheduledExecutorService == null) {
			scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		}
		return scheduledExecutorService;
	}

	public void submitTask(Runnable task) {
		this.future = getScheduledExecutorService().submit(task);
	}

	public void scheduleUpdateTimeout(Runnable onTimeout) {
		if (updateProperties.getTimeout() != null) {
			log.debug("Scheduling update timeout to {}", updateProperties.getTimeout());
			timeoutFuture = getScheduledExecutorService().schedule(onTimeout, updateProperties.getTimeout().getSeconds(), TimeUnit.SECONDS);
		} else {
			cancelUpdateTimeout();
		}
	}

	public void cancelUpdateTimeout() {
		this.cancelTo(timeoutFuture);
	}

	public void cancelTask() {
		this.cancelTo(future);
	}

	private void cancelTo(Future<?> f) {
		if (f != null) {
			f.cancel(false);
		}
	}
}
