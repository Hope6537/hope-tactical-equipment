package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @describe 驱动程序
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月9日上午11:39:34
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class WaxOMatic {
	public static void main(String[] args) throws InterruptedException {
		Car car = new Car();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new WaxOff(car));
		exec.execute(new WaxOn(car));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

/**
 * @describe 车的打蜡，抛光，等待打蜡，等待抛光任务，分别具有挂起和唤醒设定
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月9日上午11:39:44
 * @version 0.9
 * @company Changchun University&SHXT
 */
class Car {

	private boolean waxOn = false;

	public synchronized void waxed() {
		waxOn = true;
		notify();
	}

	public synchronized void buffed() {
		waxOn = false;
		notify();
	}

	public synchronized void waitForWaxing() throws InterruptedException {
		while (waxOn == false) {
			// 将会被挂起该任务 而锁被释放，从而使其他的任务也被操作，安全的改变对象的状态
			wait();
		}
	}

	public synchronized void waitForBuffing() throws InterruptedException {
		while (waxOn == true) {
			wait();
		}
	}
}

class WaxOn implements Runnable {
	private Car car;

	public WaxOn(Car car) {
		super();
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("正在打蜡 Wax On!");
				TimeUnit.MILLISECONDS.sleep(300);
				car.waxed();
				car.waitForBuffing();
			}
		} catch (Exception e) {
			System.out.println("Exit by interrupted");
		}
		System.out.println("结束打蜡任务 Ending Wax on Task");
	}
}

class WaxOff implements Runnable {
	private Car car;

	public WaxOff(Car car) {
		super();
		this.car = car;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				car.waitForWaxing();
				System.out.println("正在抛光 Wax Off");
				TimeUnit.MILLISECONDS.sleep(300);
				car.buffed();
			}
		} catch (Exception e) {
			System.out.println("Exit by interrupted");
		}
		System.out.println("结束抛光任务 Ending Wax Off task");
	}
}
