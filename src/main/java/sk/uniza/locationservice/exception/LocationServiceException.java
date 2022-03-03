package sk.uniza.locationservice.exception;

import lombok.Getter;

@Getter
public class LocationServiceException extends RuntimeException {

    private final ErrorType errorType;

    public LocationServiceException(ErrorType error) {
        super(error.getErrorMessage());
        this.errorType = error;
    }

    public LocationServiceException(ErrorType errorType, Throwable cause) {
        super(errorType.getErrorMessage(), cause);
        this.errorType = errorType;
    }
}
