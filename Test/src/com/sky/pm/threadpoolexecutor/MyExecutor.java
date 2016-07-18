package com.sky.pm.threadpoolexecutor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyExecutor extends ThreadPoolExecutor {
	
	// 声明一个私有的、ConcurrentHashMap类型的属性，并参数化为String和Date类，名为startTimes。
	private ConcurrentHashMap<String, Date> startTimes;
	
	/** 实现这个类的构造器，使用super关键字调用父类的构造器，并初始化startTime属性。
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param workQueue
	 */
	public MyExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		startTimes = new ConcurrentHashMap<String, Date>();
	}
	
	/**
	 * 覆盖beforeExecute()方法。
	 * 写入一条信息（将要执行任务的线程名和任务的哈希编码）到控制台。在HashMap中，使用这个任务的哈希编码作为key，存储开始日期。
	 */
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		System.out.printf("MyExecutor: A task is beginning: %s :%s\n", t.getName(), r.hashCode());
		startTimes.put(String.valueOf(r.hashCode()), new Date());
	}
	
	/**
	 * 覆盖afterExecute()方法。
	 * 将任务的结果和计算任务的运行时间（将当前时间减去存储在HashMap中的任务的开始时间）的信息写入到控制台。
	 */
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		Future<?> result = (Future<?>) r;
		try {
			System.out.printf("***************afterExecute******************\n");
			System.out.printf("MyExecutor: A task is finishing.\n");
			System.out.printf("MyExecutor: Result: %s\n", result.get());
			Date startDate = startTimes.remove(String.valueOf(r.hashCode()));
			Date finishDate = new Date();
			long diff = finishDate.getTime() - startDate.getTime();
			System.out.printf("MyExecutor: Duration: %d\n", diff);
			System.out.printf("*********************************\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** shutdown只是将空闲的线程interrupt()了， 因此在shutdown()之前提交的任务可以继续执行直到结束。
	 * 注：覆盖shutdown()方法。
	 * 将关于已执行的任务，正在运行的任务和待处理的任务信息写入到控制台。然后，使用super关键字调用父类的shutdown()方法。
	 */
	@Override
	public void shutdown() {
		System.out.printf("MyExecutor: Going to shutdown.\n");
		// 对于已执行的任务，使用getCompletedTaskCount()方法（获取）。
		System.out.printf("MyExecutor: Executed tasks:%d\n", getCompletedTaskCount());
		// 对于正在运行的任务，使用getActiveCount()方法（获取）。
		System.out.printf("MyExecutor: Running tasks:%d\n", getActiveCount());
		// 对于待处理任务，使用执行者存储待处理任务的阻塞队列的size()方法（获取）。
		System.out.printf("MyExecutor: Pending tasks:%d\n", getQueue().size());
		super.shutdown();
	}
	
	/**
	 * 覆盖shutdownNow()方法。
	 * 将关于已执行的任务，正在运行的任务和待处理的任务信息写入到控制台。然后，使用super关键字调用父类的shutdownNow()方法。
	 */
	@Override
	public List<Runnable> shutdownNow() {
		System.out.printf("MyExecutor: Going to immediately shutdown.\n");
		System.out.printf("MyExecutor: Executed tasks:%d\n", getCompletedTaskCount());
		System.out.printf("MyExecutor: Running tasks:%d\n", getActiveCount());
		System.out.printf("MyExecutor: Pending tasks:%d\n", getQueue().size());
		return super.shutdownNow();
	}
}