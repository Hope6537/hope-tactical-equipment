package org.hope6537.note.thinking_in_java.twenty_one;

class Sleeper extends Thread {
	private int duration;

	public Sleeper(String name, int sleepTime) {
		super(name);
		duration = sleepTime;
		start();
	}

	public void run() {
		try {
			sleep(duration);
		} catch (InterruptedException e) {
			System.out.println(getName() + " was interrupted. "
					+ "isInterrupted(): " + isInterrupted());
			return;
		}
		System.out.println(getName() + " has awakened");
	}

}

class Joiner extends Thread {
	private Sleeper sleeper;

	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}

	public void run() {
		try {
			sleeper.join();
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		System.out.println(getName() + " join completed");
	}
}

/**
 * @describe 测试Join方法 测试流程
 *           声明两个Sleeper线程任务，这两个线程将会休眠一段时间,在这段时间里，Joiner线程内部会调用sleeper
 *           .join()方法加入进线程中，并打断Sleeper任务。 当两个Joiner线程结束之后
 *           会将控制权返回给Sleeper,此时他们将接着执行睡眠操作，直到时间到达醒来。
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午1:26:20
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Joining {
	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("Sleepy", 1500), grumpy = new Sleeper(
				"Grumpy", 1500);
		Joiner dopey = new Joiner("Dopey", sleepy), doc = new Joiner("Doc",
				grumpy);
		// 他被打断 从而使之后的Joiner迅速完成操作
		grumpy.interrupt();
		// 而当Sleeper被中断或者自然结束之后，Joiner也会和Sleeper一同结束
	}
}
