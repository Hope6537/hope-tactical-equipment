package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @describe 使用显式的Lock锁
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午8:15:56
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class MutexEvenGenerator extends IntGenerator {

	private int currentEvenValue = 0;
	private Lock lock = new ReentrantLock();

	@Override
	public int next() {
		lock.lock();
		try {
			++currentEvenValue;
			Thread.yield();
			++currentEvenValue;
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		EvenChecker.test(new MutexEvenGenerator());
	}

}
