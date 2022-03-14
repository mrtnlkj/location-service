package sk.uniza.locationservice.business;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;

@Slf4j
public abstract class AbstractProcessExecutor {

	protected abstract Map<String, String> getCustomEnvProperties();

	protected abstract String getName();

	protected abstract String[] getCommand();

	public int runCommand()  {
		log.info("Process triggered by {} started with command: \"{}\", ", getName(), getCommand());
		ProcessBuilder processBuilder = new ProcessBuilder().inheritIO();
		hydrateEnvironmentProperties(processBuilder);
		int exitCode = 0;
		try {
			exitCode = processBuilder.command(getCommand())
										 .start()
										 .waitFor();
		} catch (InterruptedException | IOException e) {
			log.error("e", e);
			throw new LocationServiceException(ErrorType.DATA_STORE_CONNECTION_FAILURE_ERROR);
		}
		log.info("Process triggered by {} finished with exit code: {}.", getName(), exitCode);
		return exitCode;
	}

	private void hydrateEnvironmentProperties(ProcessBuilder processBuilder) {
		if (getCustomEnvProperties() != null && !getCustomEnvProperties().isEmpty()) {
			processBuilder.environment().putAll(getCustomEnvProperties());
		}
	}

}
