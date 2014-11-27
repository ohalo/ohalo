package org.ohalo.base.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通用sql查询 关于ResultSet的自动关闭，可以对PreparedStatement包装下，
 * 让它反回一个包装后的ResultSet,在ResultSet.next()中判断并关闭ResultSet, 觉得太麻烦了，没实现
 * 
 * @author wml
 */
public class Qsql {

	private static Log LOGGER = LogFactory.getLog(Qsql.class);
	/**
	 * 匹配 in(?)
	 */
	private static final Pattern RGX_IN = Pattern.compile("in(?)",
			Pattern.LITERAL | Pattern.CASE_INSENSITIVE);
	/**
	 * 通用的Connection，供给所有非safe的Qsql(多个线程)，可能会有阻塞等待，如果有特殊要求，建议使用safe的Qsql
	 */
	private static Connection _genericConn;
	/**
	 * 为safe的Qsql或开始事物时提供线程安全的Connection(每个线程分享一个Connection)
	 */
	private static ThreadLocal<Connection> _localConn = new ThreadLocal<Connection>();
	/**
	 * 缓存相同connection,sql的PreparedStatement，提高效率
	 * 使用ThreadLocal为每个线程单独缓存PreparedStatement
	 */
	private static HashMap<Connection, ThreadLocal<HashMap<String, PreparedStatement>>> _pstmts = new HashMap<Connection, ThreadLocal<HashMap<String, PreparedStatement>>>();
	/**
	 * 保存当前线程的事物是否开始的状态
	 */
	private static ThreadLocal<Boolean> _localBeginTransaction = new ThreadLocal<Boolean>();

	/**
	 * 这个你懂
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection createConnection() throws SQLException {
		Properties props = new Properties();
		try {
			props.load(Qsql.class.getClassLoader().getResourceAsStream(
					"conf/sql.properties"));
		} catch (IOException e) {
			throw new SQLException("fail get sql.properties!", e);
		}

		String driver = props.getProperty("sql-driver");
		String dburl = props.getProperty("sql-db-url");
		String user = props.getProperty("sql-user");
		String password = props.getProperty("sql-password");

		try {
			Class.forName(driver);// 加载sql数据驱动
			return DriverManager.getConnection(dburl, user, password);// 创建数据连接
		} catch (Exception e) {
			throw new SQLException("fail get sql connection!", e);
			// System.exit(0);//强制退出
		}
	}

	/**
	 * 获取通用Connection，不建议直接使用_genericConn获取
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getGenericConnection() throws SQLException {
		if (_genericConn == null || _genericConn.isClosed()) {// 防止连接被意外关闭
			synchronized (Qsql.class) {
				if (_genericConn == null || _genericConn.isClosed()) {
					_checkConn(_genericConn);
					_genericConn = createConnection();
				}
			}
		}
		return _genericConn;
	}

	/**
	 * 出现意外断开连接时清空所有缓存的Connection和PreparedStatement
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	private static void _checkConn(Connection conn) throws SQLException {
		if (conn != null && conn.isClosed()) {
			_localConn.remove();
			_localConn = new ThreadLocal<Connection>();
			for (ThreadLocal<HashMap<String, PreparedStatement>> local : _pstmts
					.values()) {
				local.remove();
			}
			_pstmts.clear();
		}
	}

	/**
	 * 获取当前线程的Connection实例
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getLocalConnection() throws SQLException {
		Connection conn = _localConn.get();
		if (conn == null) {
			conn = createConnection();
			_localConn.set(conn);
		}
		return conn;
	}

	/**
	 * 是否开始事物了
	 * 
	 * @return
	 */
	public static boolean isBeginTransaction() {
		return _localBeginTransaction.get() == Boolean.TRUE;
	}

	/**
	 * 开始事物， 此方法调用后，需要调用Qsql.commit()或Qsql.rollback()结束事物 (仅当前线程,仅对safe的Qsql有效)
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection beginTransaction() throws SQLException {
		Connection conn = getLocalConnection();
		conn.setAutoCommit(false);
		_localBeginTransaction.set(true);
		return conn;
	}

	/**
	 * 开始事物， 此方法调用后，需要调用Qsql.commit()或Qsql.rollback()结束事物 (仅当前线程,仅对safe的Qsql有效)
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection beginTransaction(int isolationLevel)
			throws SQLException {
		Connection conn = beginTransaction();
		conn.setTransactionIsolation(isolationLevel);
		return conn;
	}

	/**
	 * 提交事物，需要在beginTransaction后调用
	 * 
	 * @throws SQLException
	 */
	public static void commit() throws SQLException {
		if (isBeginTransaction()) {
			getLocalConnection().commit();
			endTransaction();
		}
	}

	/**
	 * 回滚事物，需要在beginTransaction后调用
	 * 
	 * @throws SQLException
	 */
	public static void rollback() throws SQLException {
		if (isBeginTransaction()) {
			getLocalConnection().rollback();
			endTransaction();
		}
	}

	/**
	 * 结束事物，已在Qsql.commit()和Qsql.rollback()中自动调用
	 * 
	 * @throws SQLException
	 */
	public static void endTransaction() throws SQLException {
		getLocalConnection().setAutoCommit(true);
		_localBeginTransaction.set(null);
	}

	private StringBuilder _sql = new StringBuilder(150);
	private List<Object> _params = new ArrayList<Object>();
	private boolean _cachePstmt;
	private boolean _safe;

	/**
	 * @param safe
	 *            true:使用线程安全的Connection(每个线程单独提供Connection), false:使用通用的
	 * @param cachePstmt
	 *            true:缓存PreparedStatement, false: 不缓存
	 * @throws SQLException
	 */
	public Qsql(boolean safe, boolean cachePstmt) {
		_safe = safe;
		_cachePstmt = cachePstmt;
	}

	public Qsql append(Object... params) {
		for (Object p : params) {
			_params.add(p);
		}
		return this;
	}

	public Qsql append(boolean append, String sql, Object... params) {
		if (append) {
			_sql.append(sql);
			return append(params);
		}
		return this;
	}

	public Qsql append(String sql, Object... params) {
		_sql.append(sql);
		return append(params);
	}

	public Qsql append(String sql) {
		_sql.append(sql);
		return this;
	}

	public Qsql(String sql) {
		_sql.append(sql);
	}

	public Qsql in(boolean append, String sql, Object... params) {
		if (append) {
			_sql.append(_prepareWhereIn(sql, params.length));
			return append(params);
		}
		return this;
	}

	public Qsql in(String sql, Object... params) {
		_sql.append(_prepareWhereIn(sql, params.length));
		return append(params);
	}

	public Qsql in(String sql, int paramsSize) {
		_sql.append(_prepareWhereIn(sql, paramsSize));
		return this;
	}

	public Qsql in(Object... params) {
		_sql = new StringBuilder(
				_prepareWhereIn(_sql.toString(), params.length));
		return append(params);
	}

	/**
	 * 获取PreparedStatement
	 * 
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement toPstmt() throws SQLException {
		PreparedStatement pstmt = _getPstmt(_sql.toString());
		for (int i = 0; i < _params.size(); i++) {
			pstmt.setObject(i + 1, _params.get(i));
		}
		if (LOGGER.isDebugEnabled()) {
			String str = pstmt.toString();
			LOGGER.debug("create sql" + str.substring(str.indexOf(':')));
		}
		return pstmt;
	}

	@Override
	public String toString() {
		try {
			return toPstmt().toString();
		} catch (Exception e) {
			return _sql.toString();
		}
	}

	/**
	 * 根据 paramsSize动态生成in(?,?,?...)号替换"in(?)"<br/>
	 * 使用where in语句<br/>
	 * 示列1: execQuery(prepareWhereIn("select * from tab where id in (%in)", 2),
	 * in1, in2);<br/>
	 * 2: execQuery(prepareWhereIn(
	 * "select * from tab where id in (%in) and pid=? ", 2), in1, in2, pid);
	 * 
	 * @param sql
	 * @param paramsSize
	 * @return
	 */
	private static String _prepareWhereIn(String sql, int paramsSize) {
		StringBuilder sb = new StringBuilder(paramsSize * 2);
		sb.append("in(");
		for (int i = 0; i < paramsSize; i++) {
			sb.append("?,");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(')');
		return RGX_IN.matcher(sql).replaceFirst(sb.toString());
	}

	/**
	 * 便捷ResultSet 关闭方法 仅仅关闭ResultSet,不关闭Statement和Connection
	 * 
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
		} catch (Exception e) {
			LOGGER.error("fail close result set!", e);
		}
	}

	private PreparedStatement _getPstmt(String sql) throws SQLException {
		Connection conn = getConnection();
		if (!_cachePstmt) {
			return conn.prepareStatement(sql);
		}
		ThreadLocal<HashMap<String, PreparedStatement>> holder = _pstmts
				.get(conn);
		HashMap<String, PreparedStatement> pstmts;
		PreparedStatement pstmt;
		if (holder == null) {
			pstmts = new HashMap<String, PreparedStatement>();
			pstmt = conn.prepareStatement(sql);
			pstmts.put(sql, pstmt);

			holder = new ThreadLocal<HashMap<String, PreparedStatement>>();
			holder.set(pstmts);

			_pstmts.put(conn, holder);
			return pstmt;
		} else {
			pstmts = holder.get();
		}

		pstmt = pstmts.get(sql);
		if (pstmt == null) {
			pstmt = conn.prepareStatement(sql);
			pstmts.put(sql, pstmt);
		}
		return pstmt;
	}

	/**
	 * 根据是否safe获取Connection
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return _safe ? getLocalConnection() : getGenericConnection();
	}

	public Object queryOne() throws SQLException {
		ResultSet rs = null;
		try {
			rs = toPstmt().executeQuery();
			Object res = null;
			if (rs.next()) {
				res = rs.getObject(1);
			}
			return res;
		} finally {
			close(rs);
		}
	}

	public ResultSet query() throws SQLException {
		return toPstmt().executeQuery();
	}

	public int update() throws SQLException {
		return toPstmt().executeUpdate();
	}

	/**
	 * 更新并获取自动增长键
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Object updateAndGet() throws SQLException {
		PreparedStatement pstmt = toPstmt();
		pstmt.executeUpdate();

		ResultSet rs = null;
		try {
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getObject(1);
			}
		} finally {
			close(rs);
		}
		return null;
	}

	/**
	 * 快捷方法,同 new Qsql
	 * 
	 * @return
	 */
	public static Qsql qsql(boolean safe, boolean cachePstmt) {
		return new Qsql(safe, cachePstmt);
	}

	/**
	 * 快捷方法, 返回非safe且缓存PreparedStatment的Qsql
	 * 
	 * @return
	 */
	public static Qsql nonsafe() {
		return new Qsql(false, true);
	}

	/**
	 * 快捷方法,同Qsql.nonsafe().append
	 * 
	 * @return
	 */
	public static Qsql nonsafe(String sql, Object... params) {
		return nonsafe().append(sql, params);
	}

	/**
	 * 快捷方法, 返回safe且缓存PreparedStatment的Qsql
	 * 
	 * @return
	 */
	public static Qsql safe() {
		return new Qsql(true, true);
	}

	/**
	 * 快捷方法,同Qsql.safe().append
	 * 
	 * @return
	 */
	public static Qsql safe(String sql, Object... params) {
		return safe().append(sql, params);
	}

	/**
	 * 示列:
	 * 
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		Object res = Qsql.nonsafe("select count(*) from T_daily").queryOne();
		print(res);

		res = Qsql.nonsafe("select count(*) from T_daily")
				.in(" where id in(?)", 1, 2, 3)
				// 注意in和append的前面要+空格， "in(?)"必须没有空格间隙
				.append(" and (state = ? or type = ?)", "online", "Music")
				.queryOne();
		// select count(*) from T_daily where id in(1,2,3) and (state = 'online'
		// or type = 'Music')
		print(res);

		String usrName = "aaaa";
		res = Qsql.nonsafe("select count(*) from T_daily")
				.in(" where id in(?)", 1, 2, 3)
				// 注意in和append的前面要+空格， "in(?)"必须没有空格间隙
				.append(" and (state = ? or type = ?)", "online", "Music")
				.append(usrName, " and user = ?", usrName)// 根据情况增加条件
				.queryOne();
		// select count(*) from T_daily where id in(1,2,3) and (state = 'online'
		// or type = 'Music')
		print(res);

		res = Qsql.nonsafe("select count(*) from T_daily")
				.in(" where id in(?)", 1, 2).append(" or type = ?", "Music")
				.queryOne();
		// select count(*) from T_daily where id in(1,2) or type = 'Music'
		print(res);

		res = Qsql
				.nonsafe("select count(*) from T_daily where usr = ?", "tester")
				.in(" and (id in(?)", 1, 2).append(" or type = ?)", "Music")
				.queryOne();
		// select count(*) from T_daily where usr = 'tester' and (id in(1,2) or
		// type = 'Music')
		print(res);

		res = Qsql.nonsafe("set phone=? from T_daily where usr = ?",
				"13577556623", "tester").update();
		// set phone='13577556623' from T_daily where usr = 'tester'
		print(res);

		ResultSet rs = null;
		try {
			rs = Qsql.nonsafe("select count(*) from T_daily")
					.in(" where id in(?) ", 1, 2, 5, 6, 7, 8)
					.append(" group by Music").query();
			// select count(*) from T_daily where id in(1,2,5,6,7,8) group by
			// Music
			while (rs.next()) {
				print(rs.getObject(1));
			}
		} finally {
			Qsql.close(rs);
		}

		// 事物
		int carId = 1;
		int userId = 1;
		int productId = 1;
		int productCount = 3;
		Qsql insertCar = Qsql
				.nonsafe(
						"insert into car(carId,productId,productCount,state) values (?,?,?)",
						carId, productId, productCount, 0);
		Qsql updateCar = Qsql.safe(
				"set state=? from car where productId=? and carId = ?", 1,
				productId, carId);
		Qsql updateUserInfo = Qsql
				.safe("set boughtProducts=boughtProducts+? from user,car where car.userId = user.userId and car.carId = ?",
						productCount, carId);

		insertCar.update();
		Qsql.beginTransaction(Connection.TRANSACTION_READ_COMMITTED);
		try {
			updateCar.update();
			updateUserInfo.update();

			pay(userId, carId, productCount);

			Qsql.commit();
		} catch (Exception e) {
			e.printStackTrace();

			Qsql.rollback();
		}
	}

	private static void pay(int userId, int carId, int productCount)
			throws Exception {
		throw new Exception("pay error");
	}

	private static void print(Object o) {
		System.out.println(o);
		System.out
				.println("**********************************************************");
	}
}
