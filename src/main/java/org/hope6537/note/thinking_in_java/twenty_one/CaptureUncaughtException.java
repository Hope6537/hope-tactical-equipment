package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

class ExceptionThread2 implements Runnable {
	public void run() {
		// 一个普通的会抛出异常的线程
		Thread t = Thread.currentThread();
		System.out.println("run() by " + t);
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		throw new RuntimeException();
	}
}

/**
 * @describe 一个异常捕获器
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午4:24:49
 * @version 0.9
 * @company Changchun University&SHXT
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("caught " + e);
	}
}

/**
 * @describe 产生线程的工厂
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午4:26:11
 * @version 0.9
 * @company Changchun University&SHXT
 */
class HandlerThreadFactory implements ThreadFactory {
	public Thread newThread(Runnable r) {
		System.out.println(this + " creating new Thread");
		Thread t = new Thread(r);
		System.out.println("created " + t);
		// 设置线程的异常捕获器
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		return t;
	}
}

public class CaptureUncaughtException {
	public static void main(String[] args) {
		// 在這裡我們就不需要try catch了
		ExecutorService exec = Executors
				.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(new ExceptionThread2());
		// 之前我们设置的都是针对具体情况逐个设计的捕获器
		// 下面的是公用共有的异常捕获器
		// 他仅仅只在目标线程内没有专有版本的捕获器的时候才针对目标工作
		// Thread.setDefaultUncaughtExceptionHandler(new
		// MyUncaughtExceptionHandler());
	}
} /*
 * Output: (90% match) HandlerThreadFactory@de6ced creating new Thread created
 * Thread[Thread-0,5,main] eh = MyUncaughtExceptionHandler@1fb8ee3 run() by
 * Thread[Thread-0,5,main] eh = MyUncaughtExceptionHandler@1fb8ee3 caught
 * java.lang.RuntimeException
 */// :~
