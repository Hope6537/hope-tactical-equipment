package org.hope6537.note.thinking_in_java.twenty_one;

public class SyncObject {

	public static void main(String[] args) {
		final DualSynch ds = new DualSynch();
		new Thread() {
			// 新建线程调用f
			public void run() {
				ds.f();
			}
		}.start();
		// 而g使用main的线程来调用
		ds.g();
	}
}

/*
 * f()方法与this对象同步，而g()则有一个同步于syncObject的临界块，因此这两个同步是互相独立的。
 */

class DualSynch {
	private Object syncObject = new Object();

	public synchronized void f() {
		for (int i = 0; i < 5; i++) {
			System.out.println("f()");
			Thread.yield();
		}
	}

	public void g() {
		synchronized (syncObject) {
			for (int i = 0; i < 5; i++) {
				System.out.println("g()");
				Thread.yield();
			}
		}
	}
}

/*
 * g() f() g() f() g() f() g() f() g() f()
 */