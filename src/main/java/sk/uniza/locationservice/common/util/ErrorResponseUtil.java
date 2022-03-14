package sk.uniza.locationservice.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseUtil {

	public static String generateRefId() {
		return UUID.randomUUID().toString();
	}

	public static String getEnhancedErrorMessageWithCause(String errorMessage, Throwable cause) {
		return cause == null ? errorMessage : String.format("%s (cause: %s)", errorMessage, cause.getMessage());
	}
}
