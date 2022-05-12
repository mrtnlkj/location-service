package sk.uniza.locationservice.controller;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.controller.error.ErrorResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseContentMatcher implements ResultMatcher {

	private final Consumer<ErrorResponse> matcher;

	private ErrorResponseContentMatcher(Consumer<ErrorResponse> matcher) {
		this.matcher = matcher;
	}

	public static ErrorResponseContentMatcher create(Consumer<ErrorResponse> matcher) {
		return new ErrorResponseContentMatcher(matcher);
	}

	public static ErrorResponseContentMatcher containsErrorType(ErrorType errorType) {
		return create(
				errorResult -> {
					assertThat(errorResult.getErrorMessage()).isNotNull();
					assertThat(errorResult.getErrorMessage().getMessage()).isNotBlank();
					assertThat(errorResult.getErrorMessage().getMessage()).contains(errorType.getErrorMessage());
				}
		);
	}

	@Override
	public void match(MvcResult result) throws Exception {
		String resultBody = result.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		matcher.accept(objectMapper.readValue(resultBody, ErrorResponse.class));
	}
}
