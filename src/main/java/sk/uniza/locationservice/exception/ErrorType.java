package sk.uniza.locationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorType implements Serializable {

    // generic error codes
    public static final ErrorType INTERNAL_SYSTEM_ERROR = new ErrorType("0000", "Internal system error", HttpStatus.INTERNAL_SERVER_ERROR);
    public static final ErrorType CONSTRAINT_VIOLATION = new ErrorType("0001", "Constraint violation", HttpStatus.BAD_REQUEST);
    public static final ErrorType METHOD_ARGUMENT_NOT_VALID = new ErrorType("0002", "Method argument not valid", HttpStatus.BAD_REQUEST);
    public static final ErrorType HANDLER_NOT_FOUND = new ErrorType("0003", "Handler not found", HttpStatus.NOT_FOUND);
    public static final ErrorType TYPE_MISMATCH = new ErrorType("0005", "Type mismatch", HttpStatus.BAD_REQUEST);

    // locations related error codes
    public static final ErrorType UPDATE_ALREADY_RUNNING = new ErrorType("0100", "Update is already in progress.", HttpStatus.INTERNAL_SERVER_ERROR);

    /**
     * Numeric string identification of error code.
     */
    private String errorCode;
    /**
     * Error message for error code.
     */
    private String errorMessage;

    /**
     * Default HTTP status code that shall be used for this error code
     */
    private HttpStatus httpResponseCode;

}
