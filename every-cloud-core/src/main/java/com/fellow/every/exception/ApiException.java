package com.fellow.every.exception;

public class ApiException extends Exception{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;


	protected ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
