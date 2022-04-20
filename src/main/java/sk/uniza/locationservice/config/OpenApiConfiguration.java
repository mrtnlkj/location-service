package sk.uniza.locationservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfiguration {

	private final Optional<BuildProperties> buildProperties;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(createInfo());
	}

	private Info createInfo() {
		return new Info()
				.title("Location Service API")
				.description("Location service provides a REST API mainly for purposes of searching localities " +
									 "across Slovak Republic based on GPS coordinates, " +
									 "with possibility of extending searched area " +
									 "by other countries in the future.")
				.license(new License().name("Licensed by Martin Lokaj"))
				.contact(new Contact().email("mrtn.lkj@gmail.com")
									  .url("https://sk.linkedin.com/in/martin-lokaj-94a679154"))
				.version(getVersion());
	}

	private String getVersion() {
		return buildProperties.map(BuildProperties::getVersion).orElse(null);
	}

	@Bean
	public ForwardedHeaderFilter getForwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}
}