package com.sky.pm.threadpoolexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		// 创建一个MyExecutor对象，名为myExecutor。
		MyExecutor myExecutor = new MyExecutor(3, 5, 1000,
				TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

		List<Future<String>> results = new ArrayList<Future<String>>();
		// 提交10个Task对象。
		for (int i = 0; i < 10; i++) {
			SleepTwoSecondsTask task = new SleepTwoSecondsTask();
			Future<String> result = myExecutor.submit(task);
			results.add(result);
		}
		// 使用get()方法，获取前5个任务的执行结果。将这些信息写入到控制台。
		for (int i = 0; i < 5; i++) {
			try {
				String result = results.get(i).get();
				System.out.printf("Main: Result for Task %d :%s\n", i, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 使用shutdown()方法结束这个执行者的执行。
		myExecutor.shutdown();
		// 使用get()方法，获取后5个任务的执行结果。将这些信息写入到控制台。
		for (int i = 5; i < 10; i++) {
			try {
				String result = results.get(i).get();
				System.out.printf("Main: Result for Task %d :%s\n", i, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		myExecutor.shutdown();
		// 使用awaitTermination()方法等待这个执行者的完成。
		try {
			myExecutor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 写入一条信息表明这个程序执行的结束。
		System.out.printf("Main: End of the program.\n");
	}
}