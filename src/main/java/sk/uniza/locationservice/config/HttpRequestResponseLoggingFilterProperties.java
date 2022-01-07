package sk.uniza.locationservice.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Slf4j
@Data
@ConfigurationProperties(prefix = "location-service.http-logging-filter")
public class HttpRequestResponseLoggingFilterProperties {

	@NotNull
	private Integer maxPayloadLength = 1000;
	@NotNull
	private Boolean enabled = Boolean.FALSE;

	@PostConstruct
	public void init() {
		log.info(this.toString());
	}
}
