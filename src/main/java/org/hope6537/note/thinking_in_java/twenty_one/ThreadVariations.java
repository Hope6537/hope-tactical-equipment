package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.TimeUnit;

/**
 * @describe 第一种包装方法 直接继承Thread类，但是这样会存在局限性 无法多重继承
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:53:37
 * @version 0.9
 * @company Changchun University&SHXT
 */
class InnerThread1 {
	private int countDown = 5;
	private Inner inner;

	private class Inner extends Thread {
		Inner(String name) {
			super(name);
			start();//直接调用Start
		}

		public void run() {
			try {
				while (true) {
					System.out.println(this);
					if (--countDown == 0)
						return;
					sleep(10);
				}
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
		}

		public String toString() {
			return getName() + ": " + countDown;
		}
	}
//	在构造方法中声明inner线程类 在inner内直接就会执行start
	public InnerThread1(String name) {
		inner = new Inner(name);
	}
}

/**
 * @describe 声明线程对象t 然后在内部进行方法实现，手动调用start启动线程
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:54:12
 * @version 0.9
 * @company Changchun University&SHXT
 */
class InnerThread2 {
	private int countDown = 5;
	private Thread t;

	public InnerThread2(String name) {
		t = new Thread(name) {
			public void run() {
				try {
					while (true) {
						System.out.println(this);
						if (--countDown == 0)
							return;
						sleep(10);
					}
				} catch (InterruptedException e) {
					System.out.println("sleep() interrupted");
				}
			}

			public String toString() {
				return getName() + ": " + countDown;
			}
		};
		t.start();
	}
}

/**
 * @describe 内部类实现Runnable接口 然后还是在类中生成线程对象再调用start启动
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:54:41
 * @version 0.9
 * @company Changchun University&SHXT
 */
class InnerRunnable1 {
	private int countDown = 5;
	private Inner inner;

	private class Inner implements Runnable {
		Thread t;

		Inner(String name) {
			t = new Thread(this, name);
			t.start();
		}

		public void run() {
			try {
				while (true) {
					System.out.println(this);
					if (--countDown == 0)
						return;
					TimeUnit.MILLISECONDS.sleep(10);
				}
			} catch (InterruptedException e) {
				System.out.println("sleep() interrupted");
			}
		}

		public String toString() {
			return t.getName() + ": " + countDown;
		}
	}

	public InnerRunnable1(String name) {
		inner = new Inner(name);
	}
}

/**
 * @describe t直接声明Thread Runnable实现
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:55:18
 * @version 0.9
 * @company Changchun University&SHXT
 */
class InnerRunnable2 {
	private int countDown = 5;
	private Thread t;

	public InnerRunnable2(String name) {
		t = new Thread(new Runnable() {
			public void run() {
				try {
					while (true) {
						System.out.println(this);
						if (--countDown == 0)
							return;
						TimeUnit.MILLISECONDS.sleep(10);
					}
				} catch (InterruptedException e) {
					System.out.println("sleep() interrupted");
				}
			}

			public String toString() {
				return Thread.currentThread().getName() + ": " + countDown;
			}
		}, name);
		t.start();
	}
}

/**
 * @describe 将线程封装进方法体内
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:55:36
 * @version 0.9
 * @company Changchun University&SHXT
 */
class ThreadMethod {
	private int countDown = 5;
	private Thread t;
	private String name;

	public ThreadMethod(String name) {
		this.name = name;
	}

	/**
	 * @descirbe 该方法线程执行之前返回
	 */
	public void runTask() {
		if (t == null) {
			t = new Thread(name) {
				public void run() {
					try {
						while (true) {
							System.out.println(this);
							if (--countDown == 0)
								return;
							sleep(10);
						}
					} catch (InterruptedException e) {
						System.out.println("sleep() interrupted");
					}
				}

				public String toString() {
					return getName() + ": " + countDown;
				}
			};
			t.start();
		}
	}
}

/**
 * @describe 线程的多种包装方法
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午12:53:21
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class ThreadVariations {
	public static void main(String[] args) {
		new InnerThread1("InnerThread1");
		new InnerThread2("InnerThread2");
		new InnerRunnable1("InnerRunnable1");
		new InnerRunnable2("InnerRunnable2");
		new ThreadMethod("ThreadMethod").runTask();
	}
}
