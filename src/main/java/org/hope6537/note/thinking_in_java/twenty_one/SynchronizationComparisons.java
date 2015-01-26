package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @describe 采用模板设计方法，将公用代码全部放在抽象类里，只有不同的地方才使用抽象方法，其他的统一标准
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午8:28:38
 * @version 0.9
 * @company Changchun University&SHXT
 */
abstract class Accumulator {
	public static long cycles = 50000L;
	private static final int N = 4;
	public static ExecutorService exec = Executors.newFixedThreadPool(N * 2);
	private static CyclicBarrier barrier = new CyclicBarrier(N * 2 + 1);
	protected volatile int index = 0;
	protected volatile long value = 0;
	protected long duration = 0;
	protected String id = "error";
	protected final static int SIZE = 100000;
	protected static int[] preLoaded = new int[SIZE];
	static {
		// 载入随机数数组
		Random rand = new Random(47);
		for (int i = 0; i < SIZE; i++)
			preLoaded[i] = rand.nextInt();
	}

	/**
	 * @descirbe 将会实现互斥对象
	 * @author Hope6537(赵鹏)
	 * @signDate 2014年8月11日下午8:22:41
	 * @version 0.9
	 */
	public abstract void accumulate();

	/**
	 * @descirbe 实现互斥对象的第二种形式
	 * @author Hope6537(赵鹏)
	 * @return
	 * @signDate 2014年8月11日下午8:22:54
	 * @version 0.9
	 */
	public abstract long read();

	// 进行长循环，同时将任务并行进行
	private class Modifier implements Runnable {
		public void run() {
			for (long i = 0; i < cycles; i++)
				accumulate();
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	private class Reader implements Runnable {
		private volatile long value;

		public void run() {
			for (long i = 0; i < cycles; i++)
				// 读取value
				value = read();
			try {
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void timedTest() {
		// 时间测试
		long start = System.nanoTime();
		for (int i = 0; i < N; i++) {
			exec.execute(new Modifier());
			exec.execute(new Reader());
		}
		// 通过两个互斥操作和任务并行操作，来观察时间
		try {
			barrier.await();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		duration = System.nanoTime() - start;
		System.out.printf("%-13s: %13d\n", id, duration);
	}

	public static void report(Accumulator acc1, Accumulator acc2) {
		System.out.printf("%-22s: %.2f\n", acc1.id + "/" + acc2.id,
				(double) acc1.duration / (double) acc2.duration);
	}
}

class BaseLine extends Accumulator {
	{
		id = "BaseLine";
	}

	public void accumulate() {
		int i = index++;
		if (i >= SIZE) {
			index = 0;
			i = 0;
		}
		// +随机数
		value += preLoaded[i];
	}

	public long read() {
		return value;
	}
}

/**
 * @describe 采用锁的方式进行测试
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午8:26:11
 * @version 0.9
 * @company Changchun University&SHXT
 */
class SynchronizedTest extends Accumulator {
	{
		id = "synchronized";
	}

	public synchronized void accumulate() {
		value += preLoaded[index++];
		if (index >= SIZE)
			index = 0;
	}

	public synchronized long read() {
		return value;
	}
}

/**
 * @describe 采用显式Lock的方式进行测试
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午8:26:19
 * @version 0.9
 * @company Changchun University&SHXT
 */
class LockTest extends Accumulator {
	{
		id = "Lock";
	}
	private Lock lock = new ReentrantLock();

	public void accumulate() {
		lock.lock();
		try {
			value += preLoaded[index++];
			if (index >= SIZE)
				index = 0;
		} finally {
			lock.unlock();
		}
	}

	public long read() {
		lock.lock();
		try {
			return value;
		} finally {
			lock.unlock();
		}
	}
}

/**
 * @describe 使用原子类进行测试
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午8:26:32
 * @version 0.9
 * @company Changchun University&SHXT
 */
class AtomicTest extends Accumulator {
	{
		id = "Atomic";
	}
	private AtomicInteger index = new AtomicInteger(0);
	private AtomicLong value = new AtomicLong(0);

	/*
	 * public void accumulate() { // Oops! Relying on more than one Atomic at //
	 * a time doesn't work. But it still gives us // a performance indicator:
	 * int i = index.getAndIncrement(); value.getAndAdd(preLoaded[i]); if (++i
	 * >= SIZE) index.set(0); }
	 */

	public void accumulate() {
		int i = index.getAndIncrement();
		if (i >= SIZE) {
			i = 0;
			index.set(0);
		}
		value.getAndAdd(preLoaded[i]);
	}

	public long read() {
		return value.get();
	}
}

public class SynchronizationComparisons {
	// 获取测试类对象
	static BaseLine baseLine = new BaseLine();
	static SynchronizedTest synch = new SynchronizedTest();
	static LockTest lock = new LockTest();
	static AtomicTest atomic = new AtomicTest();

	static void test() {
		// 拉取界面
		System.out.println("============================");
		System.out.printf("%-12s : %13d\n", "Cycles", Accumulator.cycles);
		// 同时时间测试
		baseLine.timedTest();
		synch.timedTest();
		lock.timedTest();
		atomic.timedTest();
		// 然后最后将测试结果打印出来
		Accumulator.report(synch, baseLine);
		Accumulator.report(lock, baseLine);
		Accumulator.report(atomic, baseLine);
		Accumulator.report(synch, lock);
		Accumulator.report(synch, atomic);
		Accumulator.report(lock, atomic);
	}

	public static void main(String[] args) {
		// 测试次数
		int iterations = 5;
		if (args.length > 0)
			iterations = new Integer(args[0]);
		// 第一次测试作为热身，将会使线程池填满
		System.out.println("Warmup");
		baseLine.timedTest();
		// 现在测试的时间将不会包括创造线程的时间
		// Produce multiple data points: yeah~
		for (int i = 0; i < iterations; i++) {
			test();
			Accumulator.cycles *= 2;
		}
		Accumulator.exec.shutdown();
	}

}

/*
 * Output: (Sample) Warmup BaseLine : 34237033 ============================
 * Cycles : 50000 BaseLine : 20966632 synchronized : 24326555 Lock : 53669950
 * Atomic : 30552487 synchronized/BaseLine : 1.16 Lock/BaseLine : 2.56
 * Atomic/BaseLine : 1.46 synchronized/Lock : 0.45 synchronized/Atomic : 0.79
 * Lock/Atomic : 1.76 ============================ Cycles : 100000 BaseLine :
 * 41512818 synchronized : 43843003 Lock : 87430386 Atomic : 51892350
 * synchronized/BaseLine : 1.06 Lock/BaseLine : 2.11 Atomic/BaseLine : 1.25
 * synchronized/Lock : 0.50 synchronized/Atomic : 0.84 Lock/Atomic : 1.68
 * ============================ Cycles : 200000 BaseLine : 80176670 synchronized
 * : 5455046661 Lock : 177686829 Atomic : 101789194 synchronized/BaseLine :
 * 68.04 Lock/BaseLine : 2.22 Atomic/BaseLine : 1.27 synchronized/Lock : 30.70
 * synchronized/Atomic : 53.59 Lock/Atomic : 1.75 ============================
 * Cycles : 400000 BaseLine : 160383513 synchronized : 780052493 Lock :
 * 362187652 Atomic : 202030984 synchronized/BaseLine : 4.86 Lock/BaseLine :
 * 2.26 Atomic/BaseLine : 1.26 synchronized/Lock : 2.15 synchronized/Atomic :
 * 3.86 Lock/Atomic : 1.79 ============================ Cycles : 800000 BaseLine
 * : 322064955 synchronized : 336155014 Lock : 704615531 Atomic : 393231542
 * synchronized/BaseLine : 1.04 Lock/BaseLine : 2.19 Atomic/BaseLine : 1.22
 * synchronized/Lock : 0.47 synchronized/Atomic : 0.85 Lock/Atomic : 1.79
 * ============================ Cycles : 1600000 BaseLine : 650004120
 * synchronized : 52235762925 Lock : 1419602771 Atomic : 796950171
 * synchronized/BaseLine : 80.36 Lock/BaseLine : 2.18 Atomic/BaseLine : 1.23
 * synchronized/Lock : 36.80 synchronized/Atomic : 65.54 Lock/Atomic : 1.78
 * ============================ Cycles : 3200000 BaseLine : 1285664519
 * synchronized : 96336767661 Lock : 2846988654 Atomic : 1590545726
 * synchronized/BaseLine : 74.93 Lock/BaseLine : 2.21 Atomic/BaseLine : 1.24
 * synchronized/Lock : 33.84 synchronized/Atomic : 60.57 Lock/Atomic : 1.79
 */// :~
