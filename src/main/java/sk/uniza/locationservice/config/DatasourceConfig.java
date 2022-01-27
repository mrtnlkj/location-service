package sk.uniza.locationservice.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import sk.uniza.locationservice.converters.StringToLocationTypeConverter;
import sk.uniza.locationservice.converters.StringToURLConverter;
import sk.uniza.locationservice.converters.URLToStringConverter;

@Configuration
@EnableTransactionManagement
@EnableJdbcRepositories(basePackages = {DatasourceConfig.REPOSITORY_PATH})
public class DatasourceConfig extends AbstractJdbcConfiguration {

	public static final String REPOSITORY_PATH = "sk.uniza.locationservice.repository";

	@Bean
	public DataSource getDatasource() {
		return getDatasourceProperties().initializeDataSourceBuilder()
										.build();
	}

	@Bean
	public DataSourceProperties getDatasourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	public NamedParameterJdbcOperations operations(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Override
	protected List<?> userConverters() {
		return Arrays.asList(
				new URLToStringConverter(),
				new StringToURLConverter(),
				new StringToLocationTypeConverter()
		);
	}
}
