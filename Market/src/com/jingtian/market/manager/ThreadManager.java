package com.jingtian.market.manager;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * To manage creating threads pool
 * 
 */
public class ThreadManager {
	private ThreadManager() {

	}

	private static ThreadManager instance = new ThreadManager();
	private ThreadPoolProxy longPool;
	private ThreadPoolProxy shortPool;

	public static ThreadManager getInstance() {
		return instance;
	}

	// More threads threadpool used for networking operation
	// optimal threads: cpu_core*2+1
	// synchronized: one type of the threadpool can only be created once
	public synchronized ThreadPoolProxy createLongPool() {
		if (longPool == null) {
			longPool = new ThreadPoolProxy(5, 5, 5000L);
		}
		return longPool;
	}
	// Less threads threadpool used for networking operation
	public synchronized ThreadPoolProxy createShortPool() {
		if(shortPool==null){
			shortPool = new ThreadPoolProxy(3, 3, 5000L);
		}
		return shortPool;
	}

	public class ThreadPoolProxy {
		private ThreadPoolExecutor pool;
		private int corePoolSize;
		private int maximumPoolSize;
		private long time;

		public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
			this.corePoolSize = corePoolSize;
			this.maximumPoolSize = maximumPoolSize;
			this.time = time;

		}
		/**
		 * run asynchronized task
		 * @param runnable
		 */
		public void execute(Runnable runnable) {
			if (pool == null) {
				// create thread pool
				/*
				 * 1.initial threads number in the pool
				 * 2.extra threads when initial is run out
				 * 3.If no task using threadpool, how long the pool still lasts
				 * 4.unit of the time
				 * 5.If all threads run out, where to store waiting tasks
				 */
				pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
						time, TimeUnit.MILLISECONDS,
						new LinkedBlockingQueue<Runnable>(10));
			}
			pool.execute(runnable); // run task
		}
		/**
		 * terminate asynchronized task
		 * @param runnable
		 */
		public void cancel(Runnable runnable) {
			if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
				pool.remove(runnable); // terminate task
			}
		}
	}
}
