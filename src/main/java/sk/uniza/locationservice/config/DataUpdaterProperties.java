package sk.uniza.locationservice.config;

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

@Slf4j
@Data
@EnableScheduling
@EnableAsync
@ConfigurationProperties(prefix = "location-service.data-updater")
public class DataUpdaterProperties {

	@NotNull
	@Valid
	@NestedConfigurationProperty
	private FileDownloaderProperties fileDownloader = new FileDownloaderProperties();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FileDownloaderProperties {

		@NotBlank
		private URL downloadUrl;
		@NotBlank
		private String destFileBasePath;
	}

	@PostConstruct
	public void init() {
		log.info(this.toString());
	}
}
