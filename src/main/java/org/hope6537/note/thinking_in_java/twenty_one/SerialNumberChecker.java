package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SerialNumberChecker {

	private static final int SIZE = 10;

	private static CircularSet serials = new CircularSet(1000);

	private static ExecutorService executorService = Executors
			.newCachedThreadPool();

	/**
	 * @describe 确保产生的序列唯一 如果不唯一就会输出Has + 重复数
	 * @author Hope6537(赵鹏)
	 * @signdate 2014年8月7日下午4:06:56
	 * @version 0.9
	 * @company Changchun University&SHXT
	 */
	static class SerialChecker implements Runnable {
		@Override
		public void run() {
			while (true) {
				int serial = SerialNumberGenerator.nextSerialNumber();
				if (serials.contains(serial)) {
					System.out.println("Has" + serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}
	}
	public static void run(String[] args) throws Exception {

		for (int i = 0; i < SIZE; i++) {
			executorService.execute(new SerialChecker());
		}
		if (args.length > 0) {
			TimeUnit.SECONDS.sleep(new Integer(args[0]));
			System.out.println("No");
			System.exit(0);
		}

	}

	public static void main(String[] args) throws Exception {
		run(new String[] { "4" });
	}
}

/**
 * @describe 模仿内存空间
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月7日下午4:08:54
 * @version 0.9
 * @company Changchun University&SHXT
 */
class CircularSet {
	private int[] array;

	private int len;

	private int index = 0;

	public CircularSet(int size) {
		array = new int[size];
		len = size;
		for (int i = 0; i < len; i++) {
			array[i] = -1;
		}
	}

	public synchronized void add(int i) {
		array[index] = i;
		index = ++index % len;

	}

	public synchronized boolean contains(int val) {
		for (int i = 0; i < len; i++) {
			if (val == array[i]) {
				return true;
			}
		}
		return false;
	}
}