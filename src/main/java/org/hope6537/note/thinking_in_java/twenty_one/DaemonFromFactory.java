package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @describe 后台线程工厂驱动实例
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日上午11:03:27
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class DaemonFromFactory implements Runnable {
	@Override
	public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(200);
				System.out.println(Thread.currentThread() + ":" + this);
			}
		} catch (Exception e) {
			System.out.println("Out");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// 声明由工厂模式构建出来的执行器
		ExecutorService exec = Executors
				.newCachedThreadPool(new DaemonThreadFactory());
		for (int i = 0; i < 10; i++) {
			exec.execute(new DaemonFromFactory());
		}
		System.out.println("All Daemons are started");
		TimeUnit.MILLISECONDS.sleep(2000);
		// 然后依旧遵循后台线程定理
	}
}
