package vong.piler.her.exceptions;

public class WrongNumberOfArgumentsException extends Exception {

	public WrongNumberOfArgumentsException() {
		super();
	}

	public WrongNumberOfArgumentsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongNumberOfArgumentsException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongNumberOfArgumentsException(String message) {
		super(message);
	}

	public WrongNumberOfArgumentsException(Throwable cause) {
		super(cause);
	}

}
