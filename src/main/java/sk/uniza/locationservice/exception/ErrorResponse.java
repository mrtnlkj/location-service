package sk.uniza.locationservice.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    public static final String CODE_PREFIX = "LS";
    private static final String EXAMPLE_REF_ID = "5153c9b9-151d-47e5-93ef-a67ac8663163";
    private static final RuntimeException EXAMPLE_CAUSE_OF_EXCEPTION = new RuntimeException("Example cause");

    @NotNull
    private ErrorMessage errorMessage;
    @NotNull
    @Singular
    private List<BusinessException> businessExceptions;
    @NotNull
    @Singular
    private List<FieldValidation> fieldValidations;

    public static String generateRefId() {
        return UUID.randomUUID().toString();
    }

    public static class ErrorResponseBuilder {

        public ErrorResponseBuilder fromErrorType(@NotNull ErrorType errorType, @Nullable Throwable cause, @NotNull String referenceId) {
            setReferenceId(referenceId);
            setMessage(getEnhancedErrorMessageWithCause(errorType.getErrorMessage(), cause));
            businessException(new BusinessException(CODE_PREFIX + errorType.getErrorCode(), errorType.getErrorMessage()));
            return this;
        }

        public ErrorResponseBuilder fromErrorType(@NotNull ErrorType errorType, BiConsumer<String, ErrorType> logOperation) {
            fromErrorType(errorType, null, logOperation);
            return this;
        }

        public ErrorResponseBuilder example(@NotNull ErrorType errorType) {
            fromErrorType(errorType, null, EXAMPLE_REF_ID);
            return this;
        }

        public ErrorResponseBuilder exampleWithCause(@NotNull ErrorType errorType) {
            fromErrorType(errorType, EXAMPLE_CAUSE_OF_EXCEPTION, EXAMPLE_REF_ID);
            return this;
        }

        public ErrorResponseBuilder fromErrorType(@NotNull ErrorType errorType, @Nullable Throwable cause, BiConsumer<String, ErrorType> logOperation) {
            String refId = generateRefId();
            try {
                fromErrorType(errorType, cause, refId);
                return this;
            } finally {
                logOperation.accept(refId, errorType);
            }
        }

        public ErrorResponseBuilder setMessage(String message) {
            if (errorMessage == null) {
                errorMessage = new ErrorMessage();
            }

            errorMessage.setMessage(message);
            return this;
        }

        public ErrorResponseBuilder setReferenceId(String referenceId) {
            if (errorMessage == null) {
                errorMessage = new ErrorMessage();
            }

            errorMessage.setReferenceId(referenceId);
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(
                    errorMessage,
                    businessExceptions == null ? Collections.emptyList() : businessExceptions,
                    fieldValidations == null ? Collections.emptyList() : fieldValidations
            );
        }

        private String getEnhancedErrorMessageWithCause(String errorMessage, Throwable cause) {
            return cause == null ? errorMessage : String.format("%s (cause: %s)", errorMessage, cause.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorMessage {

        @Schema(example = EXAMPLE_REF_ID)
        private String referenceId;
        @Schema(example = "Error message (cause: Cause of this error if available)")
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BusinessException {

        @Schema(example = "TSXXXX")
        private String code;
        @Schema(example = "Error message")
        private String message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldValidation {

        @Schema(example = "Field name")
        private String field;
        @Schema(example = "Reason why field validation failed")
        private String message;
    }
}
