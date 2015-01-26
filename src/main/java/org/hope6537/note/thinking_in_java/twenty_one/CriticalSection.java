package org.hope6537.note.thinking_in_java.twenty_one;

import org.hope6537.note.thinking_in_java.twenty_one.Pair.PairValuesNotEqualException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Pair {
	private int x, y;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Pair(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Pair() {
		this(0, 0);
	}

	public void incrementX() {
		x++;
	}

	public void incrementY() {
		y++;
	}

	@Override
	public String toString() {
		return "Pair [x=" + x + ", y=" + y + "]";
	}

	public class PairValuesNotEqualException extends Exception {
		public PairValuesNotEqualException() {
			super("Pair values not equals " + Pair.this);
		}
	}

	public void checkState() throws PairValuesNotEqualException {
		if (x != y) {
			throw new PairValuesNotEqualException();
		}
	}
}

/**
 * @describe 模板方法设计模式
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月7日下午4:33:58
 * @version 0.9
 * @company Changchun University&SHXT
 */
abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0);
	protected Pair p = new Pair();
	private List<Pair> storage = Collections
			.synchronizedList(new ArrayList<Pair>());

	public synchronized Pair getPair() {
		return new Pair(p.getX(), p.getY());
	}

	protected void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void increment();
}

class PairManager1 extends PairManager {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hope6537.note.thinking_in_java.twenty_one.PairManager#increment()
	 * 
	 * @author:Hope6537(赵鹏) 使用方法锁的形式
	 */
	@Override
	public synchronized void increment() {
		p.incrementX();
		p.incrementY();
		store(getPair());
	}
}

class PairManager2 extends PairManager {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hope6537.note.thinking_in_java.twenty_one.PairManager#increment()
	 * 
	 * @author:Hope6537(赵鹏) 使用同步控制块
	 */

	@Override
	public void increment() {
		Pair temp;
		synchronized (this) {
			p.incrementX();
			p.incrementY();
			temp = getPair();
		}
		store(temp);
	}
}

/**
 * @describe 用来递增Pair
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月7日下午4:55:06
 * @version 0.9
 * @company Changchun University&SHXT
 */
class PairManipulator implements Runnable {
	private PairManager pm;

	public PairManipulator(PairManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.increment();
		}
	}

	@Override
	public String toString() {
		return "PairManipulator [" + pm.getPair() + " checkCount = "
				+ pm.checkCounter.get() + "]";
	}
}

/**
 * @describe 用来同步检查是否合法Pair
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月7日下午4:55:25
 * @version 0.9
 * @company Changchun University&SHXT
 */
class PairChecker implements Runnable {
	/**
	 * @describe
	 */
	private PairManager pm;

	public PairChecker(PairManager pm) {
		super();
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			// 在每次检查成功 的时候都会递增计数器
			pm.checkCounter.incrementAndGet();
			try {
				pm.getPair().checkState();
			} catch (PairValuesNotEqualException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class CriticalSection {

	static void testApproaches(PairManager pman1, PairManager pman2) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManipulator pm1 = new PairManipulator(pman1);
		PairManipulator pm2 = new PairManipulator(pman2);
		PairChecker pcheck1 = new PairChecker(pman1);
		PairChecker pcheck2 = new PairChecker(pman2);
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pcheck1);
		exec.execute(pcheck2);
		try {
			TimeUnit.MILLISECONDS.sleep(5000);
		} catch (Exception e) {
			System.out.println("Sleep interrupted");
		}
		System.out.println("pm1 : " + pm1 + " \npm2 : " + pm2);
		System.exit(0);
	}

	/*
	 * 通过这里我们可以看出来 在单位时间内 方法上锁和代码块上锁的线程访问量 在四核八线程的电脑上超级明显的差距
	 * pm1 : PairManipulator [Pair [x=105, y=105] checkCount = 3227] 
	 * pm2 : PairManipulator [Pair [x=106, y=106] checkCount = 249052283]
	 * 这正是性能调优的重要性 在安全的情况下，使得线程能更多的访问
	 */

	public static void main(String[] args) {
		testApproaches(new PairManager1(), new PairManager2());
	}

}
