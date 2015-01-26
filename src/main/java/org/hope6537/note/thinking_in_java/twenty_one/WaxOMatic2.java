package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @describe
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月9日上午11:39:34
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class WaxOMatic2 {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff1(car));
		exec.execute(new WaxOn1(car));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

class Car1 {
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	// false为未打蜡 刚抛光完，true为未抛光 刚打蜡完
	boolean waxOn = false;

	// 打蜡
	public void waxed() {
		lock.lock();
		try {
			waxOn = true;
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	// 拋光
	public void buffed() {
		lock.lock();
		try {
			waxOn = false; // Ready for another coat of wax
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	// 抛光完毕等待打蜡
	public void waitForWaxing() throws InterruptedException {
		lock.lock();
		try {
			while (waxOn == false)
				condition.await();
		} finally {
			lock.unlock();
		}
	}

	// 打蠟完畢等待拋光
	public void waitForBuffing() throws InterruptedException {
		lock.lock();
		try {
			while (waxOn == true)
				// 在获取锁的时候才能进行唤醒，等待操作
				condition.await();
		} finally {
			lock.unlock();
		}
	}
}

class WaxOn1 implements Runnable {
	private Car car;

	public WaxOn1(Car c) {
		car = c;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("打蜡：正在打蜡!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (InterruptedException e) {
			System.out.println("打蜡被打断");
		}
		System.out.println("打蜡：结束打蜡任务");
		System.out.println("-----------------");
	}
}

class WaxOff1 implements Runnable {
	private Car car;

	public WaxOff1(Car c) {
		car = c;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("抛光：正在抛光!");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buffed();
			}
		} catch (InterruptedException e) {
			System.out.println("抛光被打断");
		}
		System.out.println("抛光：结束抛光任务");
		System.out.println("------------------");
	}
}
