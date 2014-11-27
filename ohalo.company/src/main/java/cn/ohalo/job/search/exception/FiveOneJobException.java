package cn.ohalo.job.search.exception;

/**
 * 51job 异常类处理
 * 
 * @author halo
 * @since 2013-10-5 下午7:08:13
 */
public class FiveOneJobException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5348124993846436373L;

	public static String FIVEONE_CONNECTION_URL_EXCEPTION = "51job 查询连接地址出现异常!";

	public static String FIVEONE_IO_EXCEPTION = "51job 连接信息返回读取异常!";

	public static String FIVEONE_OPENCONNECTION_PARSE_EXCETION = "51job 获取的连接解析出现异常!";

	public static String FIVEONE_PARSE_JOBORCOMPANYTD_EXCEPTION = "获取职位连接和公司连接的时候,解析html代码，出现异常";

	/**
	 * Constructs a new runtime exception with <code>null</code> as its detail
	 * message. The cause is not initialized, and may subsequently be
	 * initialized by a call to {@link #initCause}.
	 */
	public FiveOneJobException() {
		super();
	}

	/**
	 * Constructs a new runtime exception with the specified detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link #initCause}.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for later
	 *            retrieval by the {@link #getMessage()} method.
	 */
	public FiveOneJobException(String message) {
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified detail message and
	 * cause.
	 * <p>
	 * Note that the detail message associated with <code>cause</code> is
	 * <i>not</i> automatically incorporated in this runtime exception's detail
	 * message.
	 * 
	 * @param message
	 *            the detail message (which is saved for later retrieval by the
	 *            {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.4
	 */
	public FiveOneJobException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail
	 * message of <tt>(cause==null ? null : cause.toString())</tt> (which
	 * typically contains the class and detail message of <tt>cause</tt>). This
	 * constructor is useful for runtime exceptions that are little more than
	 * wrappers for other throwables.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link #getCause()} method). (A <tt>null</tt> value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 * @since 1.4
	 */
	public FiveOneJobException(Throwable cause) {
		super(cause);
	}
}
