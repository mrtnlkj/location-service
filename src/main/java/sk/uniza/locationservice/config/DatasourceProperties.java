package sk.uniza.locationservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotBlank;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class DatasourceProperties {

	@NotBlank
	private String url;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
