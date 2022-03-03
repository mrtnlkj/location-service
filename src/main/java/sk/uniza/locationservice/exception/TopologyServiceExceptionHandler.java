package sk.uniza.locationservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static sk.uniza.locationservice.exception.ErrorResponse.generateRefId;

@RestControllerAdvice
@Slf4j
public class TopologyServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolation(final ConstraintViolationException exception, final WebRequest request) {
        ErrorResponse.ErrorResponseBuilder errorResponseBuilder = createDefaultValidationBuilderForErrorType(ErrorType.CONSTRAINT_VIOLATION, exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        if (violations != null) {
            violations.forEach(
                    violation -> errorResponseBuilder.fieldValidation(new ErrorResponse.FieldValidation(
                            String.format("%s %s", violation.getRootBeanClass().getName(), violation.getPropertyPath()), violation.getMessage()
                    ))
            );
        }

        return ResponseEntity.status(ErrorType.CONSTRAINT_VIOLATION.getHttpResponseCode()).body(errorResponseBuilder.build());
    }

    @ExceptionHandler({LocationServiceException.class})
    public ResponseEntity<ErrorResponse> handleAll(LocationServiceException exception, final WebRequest request) {
        ErrorResponse errorResponse =
                ErrorResponse.builder()
                             .fromErrorType(exception.getErrorType(), (rid, err) -> log.error("TopologyService error: {} #{}", err.getErrorMessage(), rid))
                             .build();
        return ResponseEntity.status(exception.getErrorType().getHttpResponseCode()).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception exception, final WebRequest request) {
        ErrorResponse errorResponse =
                ErrorResponse.builder()
                             .fromErrorType(ErrorType.INTERNAL_SYSTEM_ERROR, exception,
                                            (rid, err) -> log.error(err.getErrorMessage() + " #" + rid, exception))
                             .build();
        return ResponseEntity.status(ErrorType.INTERNAL_SYSTEM_ERROR.getHttpResponseCode()).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(
                ex,
                ErrorResponse.builder().fromErrorType(ErrorType.HANDLER_NOT_FOUND, ex, (rid, err) -> log.error(err.getErrorMessage() + " #{}", rid)).build(),
                headers,
                ErrorType.HANDLER_NOT_FOUND.getHttpResponseCode(),
                request
        );
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse.ErrorResponseBuilder errorResponseBuilder = createDefaultValidationBuilderForErrorType(ErrorType.TYPE_MISMATCH, ex);
        String fieldName = ex instanceof MethodArgumentTypeMismatchException ? ((MethodArgumentTypeMismatchException) ex).getName() : ex.getPropertyName();
        errorResponseBuilder.fieldValidation(new ErrorResponse.FieldValidation(fieldName, ex.getMessage()));

        return handleExceptionInternal(ex, errorResponseBuilder.build(), headers, ErrorType.TYPE_MISMATCH.getHttpResponseCode(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse.ErrorResponseBuilder errorResponseBuilder = createDefaultValidationBuilderForErrorType(ErrorType.METHOD_ARGUMENT_NOT_VALID, ex);
        ex.getBindingResult().getFieldErrors()
          .forEach(fe -> errorResponseBuilder.fieldValidation(new ErrorResponse.FieldValidation(fe.getField(), fe.getDefaultMessage())));
        ex.getBindingResult().getGlobalErrors()
          .forEach(fe -> errorResponseBuilder.fieldValidation(new ErrorResponse.FieldValidation(fe.getObjectName(), fe.getDefaultMessage())));

        return handleExceptionInternal(ex, errorResponseBuilder.build(), headers, ErrorType.METHOD_ARGUMENT_NOT_VALID.getHttpResponseCode(), request);
    }

    private ErrorResponse.ErrorResponseBuilder createDefaultValidationBuilderForErrorType(ErrorType errorType, Exception exception) {
        String refId = generateRefId();
        log.error(errorType.getErrorMessage() + ": {} #{}", exception.getMessage(), refId);

        return ErrorResponse.builder().fromErrorType(errorType, exception, refId);
    }
}