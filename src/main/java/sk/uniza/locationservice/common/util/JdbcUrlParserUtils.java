package sk.uniza.locationservice.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.postgresql.PGProperty;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JdbcUrlParserUtils {

	public static String getHostFromJdbcUrl(String jdbcUrl) {
		Properties props = getPropertiesFromUrl(jdbcUrl);
		return props.getProperty(PGProperty.PG_HOST.getName());
	}

	public static String getPortFromJdbcUrl(String jdbcUrl) {
		Properties props = getPropertiesFromUrl(jdbcUrl);
		return props.getProperty(PGProperty.PG_PORT.getName());
	}

	public static String getDatabaseNameFromJdbcUrl(String jdbcUrl) {
		Properties props = getPropertiesFromUrl(jdbcUrl);
		return props.getProperty(PGProperty.PG_DBNAME.getName());
	}

	private static Properties getPropertiesFromUrl(String jdbcUrl) {
		return org.postgresql.Driver.parseURL(jdbcUrl, null);
	}
}
