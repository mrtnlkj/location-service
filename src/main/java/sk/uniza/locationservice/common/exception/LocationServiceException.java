package sk.uniza.locationservice.common.exception;

import lombok.Getter;

import sk.uniza.locationservice.common.ErrorType;

public class LocationServiceException extends RuntimeException {

	@Getter
	private final transient ErrorType errorType;

	public LocationServiceException(ErrorType error) {
		super(error.getErrorMessage());
		this.errorType = error;
	}

	public LocationServiceException(ErrorType errorType, Throwable cause) {
		super(errorType.getErrorMessage(), cause);
		this.errorType = errorType;
	}
}
