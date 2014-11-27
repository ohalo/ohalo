package org.ohalo.base.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * 功能：DBUtils 操作数据库的工具类
 *  主要用于数据库的批量插入和数据库的读写操作
 *  此为纯java的jdbc连接，以jdbc的形式进行数据的CRUD操作
 * 作者：赵辉亮
 * 日期：2013-5-10上午10:32:06
 * </pre>
 */
public class DBUtils {

  // 日志打印
  private static Log logger = LogFactory.getLog(DBUtils.class);

  // 数据url地址
  private static String DATAURL = "";
  // 访问数据库的用户
  private static String DATAUSER = "";
  // 访问数据库的密码
  private static String DATAPASSWORD = "";

  // 数据库的连接对象
  private static Connection connection = null;

  /**
   * 
   * <pre>
   * 方法体说明：获取连接实例对象，此为单例模式
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @return
   */
  private static Connection getConnectionInstance() {
    // 判断如果连接为空的话，则直接实例化一个新的连接对象
    if (connection == null) {
      try {
        // 驱动管理实例化一个新的连接对象
        connection = DriverManager.getConnection(DATAURL, DATAUSER,
            DATAPASSWORD);
        // 抛出异常信息
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return connection;
  }

  /**
   * 
   * <pre>
   * 方法体说明：实例化一个新的连接单例对象
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param connection
   *          数据库连接对象
   * @return
   */
  public static DBUtils getNewInstance(Connection connection) {

    // 实例化dbutil工具类，然后获取连接
    DBUtils dbutil = new DBUtils();
    DBUtils.connection = connection;
    return dbutil;
  }

  /**
   * 
   * <pre>
   * 方法体说明：传递 数据库url地址，数据库用户，数据库密码，数据库驱动 ， 获取dbutils的实例信息
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param dataurl
   *          访问数据库的url地址
   * @param datauser
   *          访问数据库的用户信息
   * @param datapassword
   *          访问数据库的密码信息
   * @param datadriver
   *          访问数据库的驱动
   * @return
   */
  public static DBUtils getNewInstance(String dataurl, String datauser,
      String datapassword, String datadriver) {
    // 实例化数据库工具类
    DBUtils dbutil = new DBUtils();
    // 设置驱动
    dbutil.setDriver(datadriver);
    // 设置密码
    dbutil.setPassword(datapassword);
    // 设置用户
    dbutil.setUser(datauser);
    // 设置url地址
    dbutil.setUrl(dataurl);
    // 通过以上的信息，创建connection连接
    connection = getConnectionInstance();
    return dbutil;
  }

  /**
   * 
   * <pre>
   * 方法体说明：批量执行sql语句 
   *  把sql语句封装成一个list集合中，然后对集合进行遍历，批量执行这些sql语句信息
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param sqls
   *          sql语句集合
   * @return
   */
  public int[] executeSQLBatch(List<String> sqls) {
    // 记录开始时间
    logger.info("DBUtils.executeSQLBatch开始时间:" + new Date().getTime());
    // statement 设置
    Statement prepareStatement = null;
    // 返回结果集设置
    int[] returnResult = null;
    try {
      // 创建statement对象
      prepareStatement = connection.createStatement();
      // 设置不可以自动提交
      connection.setAutoCommit(false);
      // 如果sql 等于 null 并且 sqls 为空的话，则直接返回空
      if (sqls == null || sqls.isEmpty()) {
        return returnResult;
      }
      // 设置返回结果容器大小
      returnResult = new int[sqls.size()];

      // 遍历这个sql语句，并批量执行这些sql语句信息
      for (int i = 0; i < sqls.size(); i++) {
        try {
          // 执行sql语句信息
          prepareStatement.addBatch(sqls.get(i));
          // 返回执行结果信息。如果执行成功则返回1，如果执行失败则返回-1
          returnResult[i] = 1;
          // 如果执行失败则打印异常信息，并将返回结果集设置成-1
        } catch (Exception e) {
          logger.error("DBUtils.executeSQLBatch addbatch时候出现异常! 插入第" + i
              + "条异常对象信息" + sqls.get(i).toString(), e);
          returnResult[i] = -1;
        }
      }
      // 执行批量插入的操作
      returnResult = prepareStatement.executeBatch();
      // 提交
      connection.commit();
    } catch (SQLException e) {
      // 记录异常信息
      logger.error("DBUtils.executeSQLBatch 获取链接出现异常!", e);
    } finally {
      try {
        // 把打开的statement设置成空
        if (prepareStatement != null) {
          prepareStatement.close();
          prepareStatement = null;
        }
        // 关闭数据库连接
        closeConnection();
        // 打印日志
      } catch (SQLException e) {
        logger.error("DBUtils.executeSQLBatch 关闭流的时候出现异常!", e);
      }
    }

    // 打印日志 显示执行方法完成的结束时间
    logger.info("DBUtils.executeSQLBatch 结束时间:" + new Date().getTime());
    return returnResult;
  }

  /**
   * 
   * <pre>
   * 方法体说明：关闭数据库连接 
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @throws SQLException
   */
  private void closeConnection() throws SQLException {
    if (connection != null) {
      connection.close();
      connection = null;
    }
  }

  /**
   * 
   * <pre>
   * 方法体说明：设置访问数据库url地址
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param url
   */
  private void setUrl(String url) {
    DBUtils.DATAURL = url;
  }

  /**
   * 
   * <pre>
   * 方法体说明：设置访问数据库的用户
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param user
   */
  private void setUser(String user) {
    DBUtils.DATAUSER = user;
  }

  /**
   * 
   * <pre>
   * 方法体说明：设置访问数据库的密码
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param password
   */
  private void setPassword(String password) {
    DBUtils.DATAPASSWORD = password;
  }

  /**
   * 
   * <pre>
   * 方法体说明：设置访问数据库的驱动
   * 作者：赵辉亮
   * 日期：2013-7-25
   * </pre>
   * 
   * @param driver
   */
  private void setDriver(String driver) {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      logger.error("Failed to load driver", e);
    }
  }
}
