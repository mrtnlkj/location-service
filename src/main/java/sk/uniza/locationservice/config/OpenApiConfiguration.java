package sk.uniza.locationservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
		return new OpenAPI().info(createInfo());
	}

	private Info createInfo() {
		return new Info().title("Location Service API").description("Location Service provides a REST API mainly for purposes of searching localities " +
																			"across Slovak Republic by GPS coordinates and many other various ways." +
																			"\n\n\n" +
																			"The Location-Service API data comes from the OpenStreetMap database and their usage is restricted by ODbl - Open Database licence: https://opendatacommons.org/licenses/odbl/1-0/")
						 .contact(new Contact().name("Martin Lokaj").email("mrtn.lkj@gmail.com")).version(getVersion());
	}

	private String getVersion() {
		return buildProperties.map(BuildProperties::getVersion).orElse(null);
	}

	@Bean
	public ForwardedHeaderFilter getForwardedHeaderFilter() {
		return new ForwardedHeaderFilter();
	}
}