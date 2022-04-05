package sk.uniza.locationservice.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.Duration;

@Slf4j
@Data
@EnableScheduling
@EnableAsync
@ConfigurationProperties(prefix = "location-service.update")
public class UpdateProperties {

	@NotNull
	private Duration maxLivingDuration;

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private StartupUpdateExecutorProperties startupUpdateExecutor = new StartupUpdateExecutorProperties();

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private ScheduledUpdateExecutorProperties scheduledUpdateExecutor = new ScheduledUpdateExecutorProperties();

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private RetryUpdateExecutorProperties retryUpdateExecutor = new RetryUpdateExecutorProperties();

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private ManualUpdateExecutorProperties manualUpdateExecutor = new ManualUpdateExecutorProperties();

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private Osm2pgsqlProperties osm2pgsql = new Osm2pgsqlProperties();

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private FileDownloaderProperties fileDownloader = new FileDownloaderProperties();

	@PostConstruct
	public void init() {
		log.info(this.toString());
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class StartupUpdateExecutorProperties {

		private boolean enabled;
		private boolean forceUpdateEnabled;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ScheduledUpdateExecutorProperties {

		private boolean enabled;
		@NotBlank
		private String cron;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RetryUpdateExecutorProperties {

		private boolean enabled;
		@NotNull
		private Long maxAttemptsCount;
		@NotNull
		private Duration durationBetweenAttempts;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ManualUpdateExecutorProperties {

		private boolean enabled;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Osm2pgsqlProperties {

		@NotBlank
		private String basePath;
		@NotBlank
		private String exeFileName;
		@NotBlank
		private String styleFileName;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FileDownloaderProperties {

		@NotBlank
		private URL downloadUrl;
		@NotBlank
		private String destFileBasePath;
	}
}
