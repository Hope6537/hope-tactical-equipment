package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepingTask extends LiftOff {

	@Override
	public void run() {
		try {
			while (countDown-- > 0) {
				System.out.println(status());
				// 老版休眠
				// Thread.sleep(100);
				// 正是这段休眠的事件所以使得事件能够有序的进行
				TimeUnit.MILLISECONDS.sleep(100);
			}
		} catch (Exception e) {
			// 异常无法跨线程传递 所以需要各个单位自己解决
			System.out.println("Crashed!");
		}
	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new SleepingTask());
		}
		exec.shutdown();
	}
}
/*
 * #0(9) #2(9) #4(9) #3(9) #1(9) #4(8) #1(8) #3(8) #2(8) #0(8) #3(7) #0(7) #4(7)
 * #1(7) #2(7) #3(6) #4(6) #2(6) #1(6) #0(6) #2(5) #3(5) #0(5) #4(5) #1(5) #0(4)
 * #4(4) #3(4) #1(4) #2(4) #2(3) #4(3) #1(3) #3(3) #0(3) #3(2) #0(2) #2(2) #4(2)
 * #1(2) #0(1) #3(1) #1(1) #4(1) #2(1) #4(LiftOff!) #3(LiftOff!) #1(LiftOff!)
 * #2(LiftOff!) #0(LiftOff!)
 */
