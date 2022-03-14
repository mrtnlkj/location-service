package sk.uniza.locationservice.controller.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

import sk.uniza.locationservice.common.ErrorType;

import static java.util.Collections.singletonList;
import static sk.uniza.locationservice.common.util.ErrorResponseUtil.generateRefId;
import static sk.uniza.locationservice.common.util.ErrorResponseUtil.getEnhancedErrorMessageWithCause;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

	public static final String CODE_PREFIX = "ODS_R";
	private static final String EXAMPLE_REF_ID = "5153c9b9-151d-47e5-93ef-a67ac8663163";
	private static final RuntimeException EXAMPLE_CAUSE_OF_EXCEPTION = new RuntimeException("Example cause");

	@NotNull
	private ErrorMessage errorMessage;

	@NotNull
	@Builder.Default
	private List<BusinessException> businessExceptions = Collections.emptyList();

	@NotNull
	@Builder.Default
	private List<FieldValidation> fieldValidations = Collections.emptyList();

	public static ErrorResponse fromErrorType(@NotNull ErrorType errorType, String causeMsg) {
		return ErrorResponse.builder()
							.errorMessage(new ErrorMessage(generateRefId(), causeMsg))
							.businessExceptions(singletonList(
									new BusinessException(ErrorResponse.CODE_PREFIX + errorType.getErrorCode(), errorType.getErrorMessage())))
							.build();
	}

	public static ErrorResponse fromErrorType(@NotNull ErrorType errorType, @Nullable Throwable cause) {
		return fromErrorType(errorType, getEnhancedErrorMessageWithCause(errorType.getErrorMessage(), cause));
	}

	public static ErrorResponse fromErrorType(ErrorType errorType, Exception cause, List<FieldValidation> fieldValidations) {
		return ErrorResponse.builder()
							.errorMessage(new ErrorMessage(generateRefId(), getEnhancedErrorMessageWithCause(errorType.getErrorMessage(), cause)))
							.businessExceptions(singletonList(
									new BusinessException(ErrorResponse.CODE_PREFIX + errorType.getErrorCode(), errorType.getErrorMessage())))
							.fieldValidations(fieldValidations)
							.build();
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ErrorMessage {

		@Schema(example = EXAMPLE_REF_ID)
		private String referenceId;
		@Schema(example = "Error message (cause: Cause of this error if available)")
		private String message;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@SuppressWarnings("java:S2166")
	public static class BusinessException {

		@Schema(example = CODE_PREFIX + "XXXX")
		private String code;
		@Schema(example = "Error message")
		private String message;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FieldValidation {

		@Schema(example = "Field name")
		private String field;
		@Schema(example = "Reason why field validation failed")
		private String message;
	}
}
