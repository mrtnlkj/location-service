package sk.uniza.locationservice.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import sk.uniza.locationservice.common.ErrorType;
import sk.uniza.locationservice.common.exception.LocationServiceException;

import static java.util.Collections.singletonList;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestControllerAdvice
@Slf4j
public class LocationServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String EXCEPTION_THROWN = "Exception thrown: ";

	@ExceptionHandler({LocationServiceException.class})
	public ResponseEntity<Object> handlePresenceDataManagerExceptions(LocationServiceException exception, WebRequest request) {
		log.error(EXCEPTION_THROWN, exception);

		ErrorType errorType = exception.getErrorType();

		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, exception.getCause());
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorType.getHttpResponseCode());
	}

	@ExceptionHandler({ConstraintViolationException.class})
	public ResponseEntity<ErrorResponse> handleConstraintViolation(final ConstraintViolationException exception, final WebRequest request) {
		log.info("handleConstraintViolation() with message: {}", exception.getMessage());

		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		List<ErrorResponse.FieldValidation> fieldValidations = Collections.emptyList();
		if (!isEmpty(violations)) {
			fieldValidations = violations.stream()
										 .map(v -> new ErrorResponse.FieldValidation(
												 String.format("%s %s", v.getRootBeanClass().getName(),
															   v.getPropertyPath()), v.getMessage()))
										 .collect(Collectors.toList());

		}

		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;
		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, exception, fieldValidations);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorType.getHttpResponseCode());
	}

	/*
	 * PostgreSQL JDBC connection failure
	 */
	@ExceptionHandler({CannotGetJdbcConnectionException.class, PSQLException.class, CannotCreateTransactionException.class})
	public ResponseEntity<Object> handleCassandraConnectionException(Exception exception, WebRequest request) {
		log.error(EXCEPTION_THROWN, exception);

		ErrorType errorType = ErrorType.JDBC_CONNECTION_FAILURE_ERROR;

		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, exception);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorType.getHttpResponseCode());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleDefaultException(Exception exception, WebRequest request) {
		log.error(EXCEPTION_THROWN, exception);

		ErrorType errorType = ErrorType.INTERNAL_SYSTEM_ERROR;

		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, exception);
		return new ResponseEntity<>(errorResponse, new HttpHeaders(), errorType.getHttpResponseCode());
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorType errorType = ErrorType.HANDLER_NOT_FOUND;
		return handleExceptionInternal(ex, ErrorResponse.fromErrorType(errorType, ex), headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;
		List<ErrorResponse.FieldValidation> fieldValidations = ex.getFieldErrors().stream()
																 .map(fe -> new ErrorResponse.FieldValidation(fe.getField(), fe.getDefaultMessage()))
																 .collect(Collectors.toList());
		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, ex, fieldValidations);
		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;

		String fieldName = ex instanceof MethodArgumentTypeMismatchException ? ((MethodArgumentTypeMismatchException) ex).getName() : ex.getPropertyName();
		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, ex, singletonList(new ErrorResponse.FieldValidation(fieldName, ex.getMessage())));

		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
																  WebRequest request) {
		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;
		List<ErrorResponse.FieldValidation> fieldValidations = Stream.concat(
				ex.getBindingResult().getFieldErrors()
				  .stream()
				  .map(fe -> new ErrorResponse.FieldValidation(fe.getField(), fe.getDefaultMessage())),
				ex.getBindingResult().getGlobalErrors()
				  .stream()
				  .map(fe -> new ErrorResponse.FieldValidation(fe.getObjectName(), fe.getDefaultMessage()))
		).collect(Collectors.toList());

		ErrorResponse errorResponse = ErrorResponse.fromErrorType(errorType, ex, fieldValidations);
		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status,
																		  WebRequest request) {
		if (ex instanceof MissingRequestHeaderException) {
			ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;

			MissingRequestHeaderException exception = (MissingRequestHeaderException) ex;
			ErrorResponse errorResponse = ErrorResponse
					.fromErrorType(errorType, exception, singletonList(new ErrorResponse.FieldValidation(exception.getHeaderName(), exception.getMessage())));

			return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
		}

		// fallback to generic 500 err if we don't know what kind of exception it is exactly
		return handleDefaultException(ex, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status,
																		  WebRequest request) {
		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;
		ErrorResponse errorResponse = ErrorResponse
				.fromErrorType(errorType, ex, singletonList(new ErrorResponse.FieldValidation(ex.getParameterName(), ex.getMessage())));

		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorType errorType = ErrorType.CONSTRAINT_VIOLATION;
		ErrorResponse errorResponse = ErrorResponse
				.fromErrorType(errorType, ex, singletonList(new ErrorResponse.FieldValidation(ex.getVariableName(), ex.getMessage())));

		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status,
																	 WebRequest request) {
		ErrorType errorType = ErrorType.INVALID_INPUT_DATA_ERROR;
		ErrorResponse errorResponse = ErrorResponse
				.fromErrorType(errorType, ex, singletonList(new ErrorResponse.FieldValidation(ex.getRequestPartName(), ex.getMessage())));

		return handleExceptionInternal(ex, errorResponse, headers, errorType.getHttpResponseCode(), request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<>(body, headers, status);
	}
}
