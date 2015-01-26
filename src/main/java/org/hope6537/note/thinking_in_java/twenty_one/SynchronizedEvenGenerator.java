package org.hope6537.note.thinking_in_java.twenty_one;

/**
 * @describe 使用synchronized来进行上锁操作
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午8:00:53
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class SynchronizedEvenGenerator extends IntGenerator {
	private int currentValue = 0;

	@Override
	public synchronized int next() {
		// 上锁了之后就安全了
		++currentValue;
		// 对yield的调用提高Value在奇数时上下文切换的可能性 但是上锁了就不可能会有切换
		Thread.yield();
		++currentValue;
		return currentValue;
	}

	public static void main(String[] args) {
		EvenChecker.test(new SynchronizedEvenGenerator());
	}
}
