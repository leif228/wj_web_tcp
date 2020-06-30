/**
* @title: ThreadUtils.java
* @package com.duoqio.vod.util
* @describe 
* @author tanghu
* @date 2019年6月20日
* @version V1.0
*/
package com.wujie.app.framework.systemlog;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author tanghu
 * @date 2019年6月20日
 * @describe 线程池
 * @version V1.0
 */
public class ThreadUtils extends org.apache.commons.lang3.ThreadUtils {

	/**
	 * 线程缓冲队列
	 */
	private static BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<>(100);
	/**
	 * 核心线程数，会一直存活，即使没有任务，线程池也会维护线程的最少数量
	 */
	private static final int SIZE_CORE_POOL = 5;
	/**
	 * 线程池维护线程的最大数量
	 */
	private static final int SIZE_MAX_POOL = 10;
	/**
	 * 线程池维护线程所允许的空闲时间
	 */
	private static final long ALIVE_TIME = 2000;

	private static ThreadPoolExecutor pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME,
			TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());

	static {
		pool.prestartAllCoreThreads();
	}

	public static ThreadPoolExecutor getPool() {
		return pool;
	}

}
