package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @describe 使用原子类进行安全递增操作
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月7日下午4:15:29
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class AtomicEvenGenerator extends IntGenerator {
	private AtomicInteger currentValue = new AtomicInteger(0);

	@Override
	public int next() {
		return currentValue.addAndGet(2);
	}

	public static void main(String[] args) {
		EvenChecker.test(new AtomicEvenGenerator());
	}
}
