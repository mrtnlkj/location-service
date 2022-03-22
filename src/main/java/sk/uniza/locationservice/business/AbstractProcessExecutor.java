package sk.uniza.locationservice.business;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
public abstract class AbstractProcessExecutor {

	protected abstract Map<String, String> getCustomEnvProperties();

	protected abstract String getName();

	protected abstract String[] getCommand();

	public int runCommand() {
		log.info("Process triggered by {} started with command: \"{}\", ", getName(), getCommand());
		ProcessBuilder processBuilder = new ProcessBuilder().inheritIO();
		setCustomEnvProperties(processBuilder);
		try {
			int exitCode = processBuilder.command(getCommand())
										 .start()
										 .waitFor();
			log.info("Process triggered by {} finished with exit code: {}.", getName(), exitCode);
			return exitCode;
		} catch (InterruptedException | IOException e) {
			log.error("e", e);
			throw new LocationServiceException(ErrorType.JDBC_CONNECTION_FAILURE_ERROR, e);
		}

	}

	private void setCustomEnvProperties(ProcessBuilder processBuilder) {
		if (!isEmpty(getCustomEnvProperties())) {
			processBuilder.environment().putAll(getCustomEnvProperties());
		}
	}

}
