package sk.uniza.locationservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sk.uniza.locationservice.common.http.HttpRequestResponseLoggingFilter;

@Slf4j
@Configuration
@EnableConfigurationProperties({
		HttpRequestResponseLoggingFilterProperties.class,
		UpdateProperties.class
})
@RequiredArgsConstructor
public class LocationServiceConfiguration {

	private final HttpRequestResponseLoggingFilterProperties httpRequestResponseLoggingFilterProperties;

	@Bean
	@ConditionalOnProperty(value = "location-service.http-logging-filter.enabled", havingValue = "true")
	public HttpRequestResponseLoggingFilter httpRequestResponseLoggingFilter() {
		final int maxPayloadLength = httpRequestResponseLoggingFilterProperties.getMaxPayloadLength();
		log.info("HttpRequestResponseLoggingFilter configured with max payload length: {}", maxPayloadLength);
		return new HttpRequestResponseLoggingFilter(maxPayloadLength);
	}
}
