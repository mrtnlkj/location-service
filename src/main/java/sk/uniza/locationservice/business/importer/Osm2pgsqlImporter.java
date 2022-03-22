package sk.uniza.locationservice.business.importer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import sk.uniza.locationservice.business.AbstractProcessExecutor;
import sk.uniza.locationservice.config.properties.UpdateProperties;

import static sk.uniza.locationservice.common.util.JdbcUrlParserUtils.getDatabaseNameFromJdbcUrl;
import static sk.uniza.locationservice.common.util.JdbcUrlParserUtils.getHostFromJdbcUrl;
import static sk.uniza.locationservice.common.util.JdbcUrlParserUtils.getPortFromJdbcUrl;

@Slf4j
@Component
@RequiredArgsConstructor
public class Osm2pgsqlImporter extends AbstractProcessExecutor {

	private static final String NAME = Osm2pgsqlImporter.class.getSimpleName();
	private static final String PG_PASSWORD_ENV_PROPERTY_NAME = "PGPASSWORD";
	private static final String OSM_TABLE_PREFIX = "osm";

	private final UpdateProperties updateProperties;
	private final DataSourceProperties datasourceProperties;

	private File osm2pgsqlFile;

	public int importFile(File file) {
		this.osm2pgsqlFile = file;
		return runCommand();
	}

	@Override
	protected Map<String, String> getCustomEnvProperties() {
		Map<String, String> map = new HashMap<>();
		map.put(PG_PASSWORD_ENV_PROPERTY_NAME, datasourceProperties.getPassword());
		return map;
	}

	@Override
	protected String getName() {
		return NAME;
	}

	@Override
	protected String[] getCommand() {
		return new String[]{
				appendFileNameToExtPath(updateProperties.getOsm2pgsql().getExeFileName()),
				"--host", getHostFromJdbcUrl(datasourceProperties.getUrl()),
				"--port", getPortFromJdbcUrl(datasourceProperties.getUrl()),
				"--database", getDatabaseNameFromJdbcUrl(datasourceProperties.getUrl()),
				"--username", datasourceProperties.getUsername(),
				"--prefix", OSM_TABLE_PREFIX,
				"--style", appendFileNameToExtPath(updateProperties.getOsm2pgsql().getStyleFileName()),
				"--hstore",
				osm2pgsqlFile.getAbsolutePath(),
				};
	}

	private String appendFileNameToExtPath(String fileName) {
		return updateProperties.getOsm2pgsql().getBasePath().concat(File.separator).concat(fileName);
	}
}
