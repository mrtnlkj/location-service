package sk.uniza.locationservice.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

@Slf4j
public abstract class AbstractProcessExecutor {

	protected abstract Map<String, String> getEnvironmentProperties();

	public int runCommand(String[] cmd) throws IOException, InterruptedException {
		log.info("Process with command \"{}\" started.", (Object) cmd);
		ProcessBuilder processBuilder = new ProcessBuilder().inheritIO();
		if (getEnvironmentProperties() != null) {
			processBuilder.environment().putAll(getEnvironmentProperties());
		}
		processBuilder.command(cmd);
		Process process = processBuilder.start();
		int exitCode = process.waitFor();
		log.info("Process finished with exit code: {}.", exitCode);
		return exitCode;
	}

}
