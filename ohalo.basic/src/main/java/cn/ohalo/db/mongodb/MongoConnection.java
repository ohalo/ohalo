package cn.ohalo.db.mongodb;

import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * 
 * @author halo
 * 
 */
public class MongoConnection {

	private static Mongo mg = null;
	private static DB db;
	private static String dbName = "temp";

	/**
	 * <pre>
	 *  当我们执行如下操作时(将"name"为"lily"的"age"设置为20)：
	 *  db.update({"name":"lily"},{"$set":{"age":20}})
	 *  默认情况下，该操作会使用WriteConcern.NORMAL（仅在网络错误时抛出异常），等同于：
	 *  db.update({"name":"lily"},{"$set":{"age":20}},WriteConcern.NORMAL)
	 *  使用NORMAL模式参数，可以使得写操作效率非常高。但是如果此时服务器出错，也不会返回错误给客户端，而客户端会误认为操作成功。
	 *  因此在很多重要写操作中需要使用WriteConcern.SAFE模式，保证可以感知到这个错误，保证客户端和服务器对一次操作的正确性认知保持一致。
	 *  （根据笔者测试，如果服务器发生掉电情况，客户端依然得不到当时操作的错误返回，需要特别注意）
	 *  另外在很多时候，我们需要确切知道这次写操作是否成功（或者本次更新操作影响了多少个对象），这时候就需要：
	 *  <code>
	 *  WriteResult ret = db.update({"name":"lily"},{"$set":{"age":20}});
	 *  if(ret.getN()>0) //操作影响的对象个数
	 *      return true;
	 *  else
	 *      return false;
	 *  或者：
	 *  WriteResult ret = db.update({"name":"lily"},{"$set":{"age":20}});
	 *  if(ret.getLastError() == null)
	 *      return true;
	 *  else
	 *      return false;
	 * </code>
	 *  此时，getLastError()会查询上次操作结果是否出现错误。
	 *  更进一步
	 *  然后由于mongodb中使用连接池的原因，getLastError()需要再次从连接池中获取连接，这样效率会慢一些。可以这样做：
	 *   <code>
	 *  db.requestStart();
	 *  WriteResult ret = db.update({"name":"lily"},{"$set":{"age":20}});
	 *  if(ret.getLastError() == null)
	 *      return true;
	 *  else
	 *      return false;
	 *  db.requestDone();
	 *  </code>
	 *  就可以保证update操作和getLastError()使用同一个连接，并且减少了一次存/取连接的过程。
	 *   
	 *  还有一个方法
	 *  此时也可以使用WriteConcern.SAFE参数：
	 *   <code>
	 *  WriteResult ret = db.update({"name":"lily"},{"$set":{"age":20}}, WriteConcern.SAFE);
	 *  if(ret.getLastError() == null)
	 *      return true;
	 *  else
	 *      return false;
	 *  
	 *  // is equivalent to 
	 *  db.requestStart();
	 *  WriteResult ret = db.update({"name":"lily"},{"$set":{"age":20}});
	 *  if(ret.getLastError() == null)
	 *      return true;
	 *  else
	 *      return false;
	 *  db.requestDone();
	 *  </code>
	 *  这也是我推荐使用的方式，这样即可以高效的得到返回结果，还能感知到服务器错误，一举两得。
	 * </pre>
	 * 
	 * @param concern
	 *            <ul>
	 *            首先列一下WriteConcern的几种抛出异常的级别参数：
	 *            <li>WriteConcern.NONE:没有异常抛出
	 *            <li>WriteConcern.NORMAL:仅抛出网络错误异常，没有服务器错误异常
	 *            <li>WriteConcern.SAFE:抛出网络错误异常、服务器错误异常；并等待服务器完成写操作。
	 *            <li>WriteConcern.MAJORITY: 抛出网络错误异常、服务器错误异常；并等待一个主服务器完成写操作。
	 *            <li>WriteConcern.FSYNC_SAFE:
	 *            抛出网络错误异常、服务器错误异常；写操作等待服务器将数据刷新到磁盘。
	 *            <li>
	 *            WriteConcern.JOURNAL_SAFE:抛出网络错误异常、服务器错误异常；写操作等待服务器提交到磁盘的日志文件。
	 *            <li>
	 *            WriteConcern.REPLICAS_SAFE:抛出网络错误异常、服务器错误异常；等待至少2台服务器完成写操作。
	 *            </ul>
	 */
	static void setWriteConcern(WriteConcern concern) {
		mg.setWriteConcern(concern);
	}

	private static Log logger = LogFactory.getLog(MongoConnection.class);

	@SuppressWarnings("deprecation")
	static void initMongodb() {
		if (mg == null) {
			try {
				// mg = new Mongo();
				mg = new Mongo("localhost", 27017);
			} catch (UnknownHostException e) {
				logger.error("连接mongodb数据库端口错误！请检查端口是否开启.", e);
				return;
			} catch (MongoException e) {
				logger.error("连接mongodb数据库错误！请检查数据库是否开启.", e);
				return;
			}
		}
		if (db == null) {
			// 获取temp DB；如果默认没有创建，mongodb会自动创建
			db = mg.getDB(dbName);
		}
	}

	static DBCollection initCollection(String collectionName) {
		if (StringUtils.isBlank(collectionName)) {
			logger.error("collectionName为空，请检查是否有参数传入!");
			return null;
		}
		if (db == null) {
			initMongodb();
		}
		DBCollection collection = db.getCollection(collectionName);
		return collection;
	}
}
