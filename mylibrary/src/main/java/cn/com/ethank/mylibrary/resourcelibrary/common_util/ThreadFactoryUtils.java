package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadFactoryUtils {

	// cpu核心数
	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	// 核心线程数
	private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
	// 线程池最大容量
	private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
	// 线程闲置存活时长
	private static final long KEEP_ALIVE = 10L;
	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private final AtomicInteger mCount = new AtomicInteger(1);

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "imageLoader#" + mCount.getAndIncrement());
		}
	};
	// 创建线程池
	public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
			new LinkedBlockingQueue<Runnable>(), sThreadFactory);

}
