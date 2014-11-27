/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: ThreadUtils.java 1211 2010-09-10 16:20:45Z calvinxiu $
 */
package org.ohalo.base.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 
 * <pre>
 * 功能：ThreadUtils.java 线程相关的Utils函数集合.
 * 作者：calvin
 * 日期：2013-6-25下午6:03:17
 * </pre>
 */
public class ThreadUtils {

	/**
	 * 
	 * <pre>
	 * 方法体说明： sleep等待,单位毫秒,忽略InterruptedException.
	 * 作者：赵辉亮
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param millis
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// Ignore.
		}
	}

	/**
   * 
   * 
 
   */
	/**
	 * 
	 * <pre>
	 * 方法体说明：按照ExecutorService JavaDoc示例代码编写的Graceful Shutdown方法. 先使用shutdown尝试执行所有任务.
	 * 超时后调用shutdownNow取消在workQueue中Pending的任务,并中断所有阻塞函数.
	 * 另对在shutdown时线程本身被调用中断做了处理.
	 * 作者：赵辉亮
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param pool
	 * @param shutdownTimeout
	 * @param shutdownNowTimeout
	 *            TODO
	 * @param timeUnit
	 */
	public static void gracefulShutdown(ExecutorService pool,
			int shutdownTimeout, int shutdownNowTimeout, TimeUnit timeUnit) {
		pool.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
				pool.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
					System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：直接调用shutdownNow的方法.
	 * 作者：赵辉亮
	 * 日期：2013-7-26
	 * </pre>
	 * 
	 * @param pool
	 * @param timeout
	 * @param timeUnit
	 */
	public static void normalShutdown(ExecutorService pool, int timeout,
			TimeUnit timeUnit) {
		try {
			pool.shutdownNow();
			if (!pool.awaitTermination(timeout, timeUnit)) {
				System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * 
	 * 
	 * <pre>
	 * 功能：ThreadUtils.java 自定义ThreadFactory,可定制线程池的名称.
	 * 作者：赵辉亮
	 * 日期：2013-6-25下午6:03:17
	 * </pre>
	 */
	public static class CustomizableThreadFactory implements ThreadFactory {
		//
		private final String namePrefix;
		//
		private final AtomicInteger threadNumber = new AtomicInteger(1);

		/**
		 * *
		 * 
		 * @param poolName
		 */
		public CustomizableThreadFactory(String poolName) {
			namePrefix = poolName + "-";
		}

		/**
		 * 
		 * <pre>
		 * 方法体说明：(方法详细描述说明、方法参数的具体涵义)
		 * 作者：赵辉亮
		 * 日期：2013-7-26
		 * </pre>
		 * 
		 * @param runable
		 * @return
		 */
		public Thread newThread(Runnable runable) {
			return new Thread(runable, namePrefix
					+ threadNumber.getAndIncrement());
		}
	}
}
