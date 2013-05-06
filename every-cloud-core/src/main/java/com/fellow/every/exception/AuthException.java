package com.fellow.every.exception;

public class AuthException extends Exception{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected AuthException() {
        super();
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
