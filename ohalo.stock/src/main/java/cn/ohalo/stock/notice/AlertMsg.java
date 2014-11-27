package cn.ohalo.stock.notice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.ohalo.stock.entity.StockInfo;
import cn.ohalo.stock.view.VersionUtil;

/**
 * 提示消息监听，用于监听股票的提示信息，用于及时反馈，及時讓用戶知道股票信息情況
 * 
 * @author halo
 * 
 */
public class AlertMsg implements MsgNotice<String, StockInfo> {

	private Map<String, StockInfo> params = new HashMap<String, StockInfo>();
	private static Log logger = LogFactory.getLog(AlertMsg.class);
	private static AlertMsg as = null;

	private AlertMsg() {

	}

	public static AlertMsg getInstance() {
		if (as == null) {
			as = new AlertMsg();
		}
		return as;
	}

	public void printMsg() {
		final VersionUtil util = new VersionUtil();
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					Set<String> keys = params.keySet();
					if (keys == null || keys.isEmpty()) {
						continue;
					}
					for (Iterator<String> iterator = keys.iterator(); iterator
							.hasNext();) {
						String key = iterator.next();
						util.initParams("购买股票代码：" + key, params.get(key)
								.toString());
						util.start();
					}
					params.clear();
				}
			}
		});
		thread2.start();
	}

	public void put(String title, StockInfo msg) {
		logger.warn("购买股票代码：" + title + ":" + msg.toString());
		params.put(title, msg);
	}

	public StockInfo get(String title) {
		return params.get(title);
	}

	public void remove(String title) {
		params.remove(title);
	}

}