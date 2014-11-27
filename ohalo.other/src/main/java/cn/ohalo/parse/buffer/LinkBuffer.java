package cn.ohalo.parse.buffer;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.ohalo.parse.buffer.exception.BufferedInitException;
import cn.ohalo.parse.buffer.exception.BufferedStateException;
import cn.ohalo.parse.buffer.exception.SenderException;

public class LinkBuffer<T> {
	/**
	 * 阻塞队列元素的长度
	 */
	private int queueSize = 20;

	/**
	 * 队列中数组的长度
	 */
	private int listSize = 5000;

	/**
	 * 是否启动日志缓存
	 */
	private boolean enable = true;

	/**
	 * 日志发送者
	 */
	private ISenderBuffer<T> sender;

	/**
	 * 定时刷新时间间隔,单位秒,默认30分钟
	 */
	private long interval = 30L * 60 * 1000;

	/**
	 * 
	 */
	private FlushThread thread;

	/**
	 * 私有构造器
	 */
	public LinkBuffer() {
		afterPropertiesSet();
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get ()
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return
	 */
	public int getQueueSize() {
		return queueSize;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置队列大小
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param queueSize
	 */
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get list size 
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return
	 */
	public int getListSize() {
		return listSize;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：set list size 
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param listSize
	 */
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：是否启用
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：设置启用
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param enable
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：get log sender 
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return
	 */
	public ISenderBuffer<T> getSender() {
		return sender;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：set log sender 
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param sender
	 */
	public void setSender(ISenderBuffer<T> sender) {
		this.sender = sender;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：单位秒
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param interval
	 *            void
	 * @throws
	 * @author ningyu
	 */
	public void setInterval(long seconds) {
		this.interval = interval * 1000;
	}

	/**
	 * 日志缓冲开关标志
	 */
	private final AtomicBoolean shutdown = new AtomicBoolean(false);

	/**
	 * 日志集合队列
	 */
	private BlockingDeque<ArrayList<T>> queuePool;

	/**
	 * 
	 * <pre>
	 * 方法体说明：如果队列的元素个数和队列的元素的容量设置正确，进行下面操作: 1、 初始化日志缓冲 2、初始化队列中的缓冲区 init
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return void
	 * 
	 * @since JDK1.6
	 */
	private void init() {
		check();
		initQueues();
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：初始化队列中的缓冲区 initQueues
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return void
	 * 
	 * @since JDK1.6
	 */
	private void initQueues() {
		queuePool = new LinkedBlockingDeque<ArrayList<T>>(queueSize);
		for (int i = 0; i < queueSize; i++) {
			ArrayList<T> list = new ArrayList<T>(listSize);
			try {
				queuePool.put(list);
			} catch (InterruptedException e) {
				throw new BufferedInitException("LogBuffer initQueues error!");
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：检查队列的初始化元素个数、队列元素的初始化容量是否正确， 若不正确则抛出初始化异常 check
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @return void
	 * @since JDK1.6
	 */
	private void check() {
		if (listSize <= 0) {
			throw new BufferedInitException("listSize can not <= 0");
		}
		if (queueSize < 1) {
			throw new BufferedInitException("queueSize can not < 1");
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：记录日志 将需要写入日志的对象放入到日志缓冲中，每次取出日志缓冲阻塞队列中的首个日志缓冲，
	 * 并向其添加需要记录的日志信息，若日志缓冲已经达到容量限制，则通过异步jms发送该日志缓冲 并在队列的末尾添加一个新的日志缓冲
	 * 以下3种情况将不记录日志：1、禁止启用缓存；2、日志缓冲已经关闭；3、需要写入的对象为null write
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param obj
	 * @return void
	 * @since JDK1.6
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void write(T obj) {
		// 如果日志缓冲禁止,则什么都不做
		// 默认开启
		if (!enable) {
			return;
		}
		// 判断日志缓冲的状态
		// 如果已关闭则抛异常
		if (shutdown.get()) {
			throw new BufferedStateException("buffer is shutdown");
		}
		if (obj == null) {
			return;
		}
		ArrayList list;
		try {
			// 从队头取出缓冲块
			list = queuePool.takeFirst();
			// 将日志放入缓冲块
			list.add(obj);
			// 如果缓冲块已满,则新建一个发送任务交给线程池发送
			// 并且新建一个空的缓冲块放入队尾
			if (list.size() == listSize) {
				queuePool.putLast(new ArrayList<T>(listSize));
				if (sender == null) {
					throw new SenderException(
							"LogSender didn't find the corresponding configuration!");
				}
				sender.send(list);
			} else {// 否则将缓冲块放回队头
				queuePool.putFirst(list);
			}
		} catch (InterruptedException e) {
			throw new BufferedStateException("write buffer is error");
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：属性注入完毕后，调用初始化方法 初始化阻塞队列、线程池以及阻塞队列中的缓冲区
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 *      afterPropertiesSet
	 * @throws Exception
	 * @since JDK1.6
	 */
	public void afterPropertiesSet() {
		init();
		thread = new FlushThread(this.getClass().getName());
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：关闭时，发送队列中缓冲的日志
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @see org.springframework.beans.factory.DisposableBean#destroy() destroy
	 * @throws Exception
	 * @since JDK1.6
	 */
	public void destroy() {
		thread.interrupt();

		shutdown.set(true);

		ArrayList<T> list = null;
		/**
		 * 将缓存中的所有日志发送
		 */
		while ((list = queuePool.poll()) != null) {
			if (list.size() == 0) {
				continue;
			}
			if (sender == null) {
				throw new SenderException(
						"LogSender didn't find the corresponding configuration!");
			}
			sender.send(list);
		}
	}

	/**
	 * 
	 * 
	 * <pre>
	 * 功能：FrameLogBuffer.java
	 * 作者：niyu
	 * 日期：2013-6-25下午6:03:17
	 * </pre>
	 */
	private class FlushThread extends Thread {

		public FlushThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			while (true) {
				try {
					// 默认开启
					if (!enable) {
						return;
					}
					Thread.sleep(interval);
					// 判断日志缓冲的状态
					// 如果已关闭则抛异常
					if (shutdown.get()) {
						throw new BufferedStateException("buffer is shutdown");
					}
					// 从队头取出缓冲块
					ArrayList<T> list = queuePool.pollFirst();
					if (list != null && list.size() > 0) {
						// 如果缓冲块已满,则新建一个发送任务交给线程池发送
						// 并且新建一个空的缓冲块放入队尾
						queuePool.offerLast(new ArrayList<T>(listSize));
						if (sender == null) {
							throw new SenderException(
									"LogSender didn't find the corresponding configuration!");
						}
						sender.send(list);
					} else {// 否则将缓冲块放回队头
						queuePool.putFirst(list);
					}
				} catch (InterruptedException e) {
					throw new BufferedStateException("write buffer is error");
				}
			}
		}

	}

}
