package sk.uniza.locationservice.business.importer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

	public void importFile(File file) throws IOException, InterruptedException {
		this.osm2pgsqlFile = file;
		runCommand();
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
//				"/bin/sh",
//				"-c",
				updateProperties.getOsm2pgsql().getRunCmd(),
				"--host", getHostFromJdbcUrl(datasourceProperties.getUrl()),
				"--port", getPortFromJdbcUrl(datasourceProperties.getUrl()),
				"--database", getDatabaseNameFromJdbcUrl(datasourceProperties.getUrl()),
				"--username", datasourceProperties.getUsername(),
				"--prefix", OSM_TABLE_PREFIX,
				"--style", updateProperties.getOsm2pgsql().getStyleFilePath(),
				"--hstore",
				osm2pgsqlFile.getAbsolutePath(),
				};
	}
}
