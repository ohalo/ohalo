package org.ohalo.base.exception;

/**
 * 
 * @author halo
 * @since 2013-8-18 上午10:19:04
 */
public class BaseInfoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6270068257624982115L;

	/**
	 * @see RuntimeException#RuntimeException()
	 */
	public BaseInfoException() {
		super();
	}

	/**
	 * 
	 * @param message
	 * @see RuntimeException#RuntimeException(String)
	 */
	public BaseInfoException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 * 
	 * @see RuntimeException#RuntimeException(String, Throwable)
	 */
	public BaseInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 
	 * @param cause
	 * @see RuntimeException#RuntimeException(Throwable)
	 */
	public BaseInfoException(Throwable cause) {
		super(cause);
	}
}
