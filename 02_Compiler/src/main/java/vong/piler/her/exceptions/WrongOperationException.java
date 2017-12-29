package vong.piler.her.exceptions;

public class WrongOperationException extends Exception {

	private static final long serialVersionUID = -7369367584152974464L;

	public WrongOperationException() {
		super();
	}

	public WrongOperationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public WrongOperationException(String message, Throwable cause) {
		super(message, cause);
	}

	public WrongOperationException(String message) {
		super(message);
	}

	public WrongOperationException(Throwable cause) {
		super(cause);
	}
}
