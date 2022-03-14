package sk.uniza.locationservice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public final class ErrorType {

	public static final ErrorType CONSTRAINT_VIOLATION = new ErrorType("0001", "Constraint violation", HttpStatus.BAD_REQUEST);
	public static final ErrorType INVALID_INPUT_DATA_ERROR = new ErrorType("0002", "Invalid input data", HttpStatus.BAD_REQUEST);
	public static final ErrorType MISSING_MANDATORY_INPUT_ERROR = new ErrorType("0003", "Missing mandatory input(s)", HttpStatus.BAD_REQUEST);

	public static final ErrorType HANDLER_NOT_FOUND = new ErrorType("0004", "Handler not found", HttpStatus.NOT_FOUND);

	public static final ErrorType INTERNAL_SYSTEM_ERROR = new ErrorType("0000", "Internal system error", HttpStatus.INTERNAL_SERVER_ERROR);
	public static final ErrorType DATA_STORE_CONNECTION_FAILURE_ERROR = new ErrorType("0005", "Data store connection failure",
																					  HttpStatus.INTERNAL_SERVER_ERROR);

	public static final ErrorType READING_DATA_STORE_JSON_DATA_ERROR = new ErrorType("0100", "Reading JSON data from cassandra error",
																					 HttpStatus.INTERNAL_SERVER_ERROR);
	public static final ErrorType CANNOT_EXTRACT_OBJ_NODE_FROM_JSON = new ErrorType("0101", "Couldn't extract ObjectNode from JSON",
																					HttpStatus.BAD_REQUEST);
	public static final ErrorType CANNOT_READ_JSON = new ErrorType("0102", "Cannot read input as JSON", HttpStatus.BAD_REQUEST);
	public static final ErrorType UPDATE_ALREADY_RUNNING = new ErrorType("0102", "Cannot read input as JSON", HttpStatus.BAD_REQUEST);

	/**
	 * numeric string identification of error code.
	 */
	private String errorCode;
	/**
	 * Error message for error code.
	 */
	private String errorMessage;

	/**
	 * default HTTP status code that shall be used for this error code
	 */
	private HttpStatus httpResponseCode;

	/**
	 * note: number of arguments is checked against % character in message
	 */
	public ErrorType withArguments(Object... arguments) {
		int argsInMessage = this.errorMessage.split("%").length - 1;

		if (argsInMessage != arguments.length) {
			throw new IllegalArgumentException(String.format("Wrong number of arguments, provided %d but required is %d!", arguments.length, argsInMessage));
		}

		return new ErrorType(this.errorCode, String.format(this.errorMessage, arguments), this.httpResponseCode);
	}
}
