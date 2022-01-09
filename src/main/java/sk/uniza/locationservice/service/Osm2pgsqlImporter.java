package sk.uniza.locationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sk.uniza.locationservice.config.DataUpdaterProperties;
import sk.uniza.locationservice.config.DatasourceProperties;

import static sk.uniza.locationservice.util.JdbcUrlParserUtils.getDatabaseNameFromJdbcUrl;
import static sk.uniza.locationservice.util.JdbcUrlParserUtils.getHostFromJdbcUrl;
import static sk.uniza.locationservice.util.JdbcUrlParserUtils.getPortFromJdbcUrl;

@Slf4j
@Component
@RequiredArgsConstructor
public class Osm2pgsqlImporter extends ProcessRunner {

	private final DataUpdaterProperties dataUpdaterProperties;
	private final DatasourceProperties datasourceProperties;
	private static final String PG_PASSWORD_ENV_PROPERTY_NAME = "PGPASSWORD";

	private static final String OSM_TABLE_PREFIX = "osm";

	public int importFile(File file) throws IOException, InterruptedException {
		log.info("OSM Data import from file \"{}\" STARTED.", file);
		int exitCode = runCommand(buildOsm2pgsqlCmd(file));
		log.info("OSM Data import FINISHED with exit code: {}.", exitCode);
		return exitCode;
	}

	@Override
	protected Map<String, String> getEnvironmentProperties() {
		Map<String, String> map = new HashMap<>();
		map.put(PG_PASSWORD_ENV_PROPERTY_NAME, datasourceProperties.getPassword());
		return map;
	}

	protected String[] buildOsm2pgsqlCmd(File file) {
		return new String[]{
				appendFileNameToExtPath(dataUpdaterProperties.getOsm2pgsql().getExeFileName()),
				"--host", getHostFromJdbcUrl(datasourceProperties.getUrl()),
				"--port", getPortFromJdbcUrl(datasourceProperties.getUrl()),
				"--database", getDatabaseNameFromJdbcUrl(datasourceProperties.getUrl()),
				"--username", datasourceProperties.getUsername(),
				"--prefix", OSM_TABLE_PREFIX,
				"--style", appendFileNameToExtPath(dataUpdaterProperties.getOsm2pgsql().getStyleFileName()),
				"--hstore",
				file.getAbsolutePath(),
				};
	}

	private String appendFileNameToExtPath(String fileName) {
		return dataUpdaterProperties.getOsm2pgsql().getBasePath().concat(File.separator).concat(fileName);
	}
}
