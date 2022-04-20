package sk.uniza.locationservice.business.importer;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
public abstract class AbstractProcessExecutor {

	protected abstract Map<String, String> getCustomEnvProperties();

	private void setCustomEnvProperties(ProcessBuilder processBuilder) {
		if (!isEmpty(getCustomEnvProperties())) {
			processBuilder.environment().putAll(getCustomEnvProperties());
		}
	}

	protected abstract String getName();

	protected abstract String[] getCommand();

	public void runCommand() throws IOException, InterruptedException {
		log.info("Process triggered by {} started with command: \"{}\", ", getName(), getCommand());
		ProcessBuilder processBuilder = new ProcessBuilder().inheritIO();
		setCustomEnvProperties(processBuilder);
		try {
			int exitCode = processBuilder.command(getCommand())
										 .start()
										 .waitFor();
			log.info("Process triggered by {} finished with exit code: {}.", getName(), exitCode);
			if (exitCode != 0) {
				throw new IllegalStateException();
			}
		} catch (InterruptedException | IOException e) {
			log.error("AbstractProcessExecutor ERROR: ", e);
			Thread.currentThread().interrupt();
			throw e;
		}

	}

}
