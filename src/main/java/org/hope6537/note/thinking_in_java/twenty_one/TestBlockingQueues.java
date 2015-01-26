package org.hope6537.note.thinking_in_java.twenty_one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

class LiftOffRunner implements Runnable {

	private BlockingQueue<LiftOff> rockets;

	public LiftOffRunner(BlockingQueue<LiftOff> rockets) {
		super();
		this.rockets = rockets;
	}

	public void add(LiftOff lo) {
		try {
			rockets.put(lo);
		} catch (InterruptedException e) {
			System.out.println("在存入队列的时候被打断");
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				LiftOff rocket = rockets.take();
				rocket.run();
			}
		} catch (InterruptedException e) {
			System.out.println("在取出数据时被打断");
		}
		System.out.println("从起飞架退出");
		System.out.println("======================");
	}
}

public class TestBlockingQueues {
	static void getKey() {
		try {
			new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void getKey(String message) {
		System.out.println(message);
		getKey();
	}

	static void test(String msg, BlockingQueue<LiftOff> queue) {
		System.out.println(msg);
		LiftOffRunner runner = new LiftOffRunner(queue);
		Thread t = new Thread(runner);
		t.start();
		for (int i = 0; i < 5; i++) {
			runner.add(new LiftOff(5));
		}
		getKey(" 按下回车键 ( " + msg + " )");
		t.interrupt();
		System.out.println("完成" + msg + "任务");
	}

	public static void main(String[] args) {
		// 在使用阻塞队列的情况下可以不使用锁
		test("LinkedBlockingQueue", // 无限空间
				new LinkedBlockingQueue<LiftOff>());
		test("ArrayBlockingQueue", // 修正空间
				new ArrayBlockingQueue<LiftOff>(3));
		test("SynchronousQueue", // 只有一个
				new SynchronousQueue<LiftOff>());
	}
}
