package com.ohalo.log.buffer;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.ohalo.log.exception.BufferedInitException;
import com.ohalo.log.exception.BufferedStateException;
import com.ohalo.log.exception.SenderException;
import com.ohalo.log.sender.IFrameLogSender;

/**
 * 
 * <pre>
 * 功能：FrameLogBuffer 该类用来作为日志的buffer,使用队列的结构
 *  工具类的定位：
 *  1.作为一个软件开发者，缺乏想象力是最严重的罪过之一。
 *    我们经常把事情重复做一遍又一遍，但是我们很少改变这种方式。
 *    经过这些年开发，在我的工具箱里面有了一些每个项目我都需要用到的工具，
 *    烦人的重复工作不再是我的事.
 *  2.工具，汉语词语，原指工作时所需用的器具，
 *    后引申为为达到、完成或促进某一事物的手段。
 *    它的好处可以是机械性，也可以是智能性的。
 *    大部分工具都是简单机械。
 *    例如一根铁棍可以当作槓杆使用，力点离开支点越远，杠杆传递的力就越大.
 *    而工具类就是通过实现独立功能单元的一个工具
 *  3.直接使用的工具类，不但可以在本应用中使用这些工具类，也可以在其它的应用中使用，
 *    这些工具类中的大部分是可以在脱离各种框架和应用时使用的。
 *    工具类并在程序编写时适当使用和提取，将有助于提高开发效率、增强代码质量。
 *  4.对于软件开发过程中，需要使用到各种框架，而框架中往往都提供了相应的工具类。
 *    比如spring、struts、ibatis、hibernate。
 *    而java本身也提供不少的工具类供开发员进行特殊的处理
 * 作者：niyu
 * 日期：2013-5-10上午10:27:47
 * </pre>
 */
public class FrameLogBuffer implements IFrameLogBuffer, InitializingBean,
		DisposableBean {
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
	private IFrameLogSender logSender;

	/**
	 * 定时刷新时间间隔,单位秒,默认30分钟
	 */
	private long interval = 30L * 60 * 1000;

	/**
	 * 
	 */
	private FlushThread thread;

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
	public IFrameLogSender getLogSender() {
		return logSender;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：set log sender 
	 * 作者：niyu
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param logSender
	 */
	public void setLogSender(IFrameLogSender logSender) {
		this.logSender = logSender;
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
	private BlockingDeque<ArrayList<Object>> queuePool;

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
		queuePool = new LinkedBlockingDeque<ArrayList<Object>>(queueSize);
		for (int i = 0; i < queueSize; i++) {
			ArrayList<Object> list = new ArrayList<Object>(listSize);
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
	public void write(Object obj) {
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
				queuePool.putLast(new ArrayList(listSize));
				if (logSender == null) {
					throw new SenderException(
							"LogSender didn't find the corresponding configuration!");
				}
				logSender.send(list);
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
	@Override
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
	@Override
	public void destroy() {
		thread.interrupt();

		shutdown.set(true);

		ArrayList<Object> list = null;
		/**
		 * 将缓存中的所有日志发送
		 */
		while ((list = queuePool.poll()) != null) {
			if (list.size() == 0) {
				continue;
			}
			if (logSender == null) {
				throw new SenderException(
						"LogSender didn't find the corresponding configuration!");
			}
			logSender.send(list);
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
					ArrayList<Object> list = queuePool.pollFirst();
					if (list != null && list.size() > 0) {
						// 如果缓冲块已满,则新建一个发送任务交给线程池发送
						// 并且新建一个空的缓冲块放入队尾
						queuePool.offerLast(new ArrayList<Object>(listSize));
						if (logSender == null) {
							throw new SenderException(
									"LogSender didn't find the corresponding configuration!");
						}
						logSender.send(list);
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