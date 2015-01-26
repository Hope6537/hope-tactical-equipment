package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @describe 使用阻塞队列来实现 做土司-抹黄油-沾果酱-吃掉流程 不使用synchronized和显式Lock
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月10日下午1:00:53
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class ToastOMatic {
	public static void main(String[] args) throws Exception {
		ToastQueue dryQueue = new ToastQueue(), butteredQueue = new ToastQueue(), finishedQueue = new ToastQueue();
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new Toaster(dryQueue));
		exec.execute(new Butterer(dryQueue, butteredQueue));
		exec.execute(new Jammer(butteredQueue, finishedQueue));
		exec.execute(new Eater(finishedQueue));
		TimeUnit.SECONDS.sleep(5);
		exec.shutdownNow();
	}
}

class Toast {
	public enum Status {
		DRY, BUTTERED, JAMMED
	};

	private Status status = Status.DRY;

	private final int id;

	public Toast(int id) {
		super();
		this.id = id;
	}

	public void butter() {
		status = Status.BUTTERED;
	}

	public void jam() {
		status = Status.JAMMED;
	}

	public Status getStatus() {
		return status;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "土司 [状态=" + status + ", 编号=" + id + "]";
	}

}

class ToastQueue extends LinkedBlockingQueue<Toast> {
	/**
	 * @describe
	 */
	private static final long serialVersionUID = 7496613851118253846L;
}

class Toaster implements Runnable {
	private ToastQueue toastQueue;
	private int count = 0;
	private Random rand = new Random(47);

	public Toaster(ToastQueue toastQueue) {
		super();
		this.toastQueue = toastQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
				// 做土司 向队列中添加基础对象
				Toast t = new Toast(count++);
				System.out.println(t);
				toastQueue.put(t);
			}
		} catch (Exception e) {
			System.out.println("做土司被中断");
		}
		System.out.println("做完土司");
	}
}

class Butterer implements Runnable {
//	从队列中获取基础对象
	private ToastQueue dryQueue, butteredQueue;

	public Butterer(ToastQueue dryQueue, ToastQueue butteredQueue) {
		super();
		this.dryQueue = dryQueue;
		this.butteredQueue = butteredQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = dryQueue.take();
				t.butter();
				System.out.println(t);
				butteredQueue.put(t);
			}
		} catch (Exception e) {
			System.out.println("抹黄油被中断");
		}
		System.out.println("抹完黄油");
	}
}

class Jammer implements Runnable {
	private ToastQueue butteredQueue, finishedQueue;

	public Jammer(ToastQueue butteredQueue, ToastQueue finishedQueue) {
		super();
		this.butteredQueue = butteredQueue;
		this.finishedQueue = finishedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = butteredQueue.take();
				t.jam();
				System.out.println(t);
				finishedQueue.put(t);
			}
		} catch (Exception e) {
			System.out.println("沾果酱被中断");
		}
		System.out.println("沾完果酱");
	}
}

class Eater implements Runnable {
	private ToastQueue finishedQueue;
	private int counter = 0;

	public Eater(ToastQueue finishedQueue) {
		super();
		this.finishedQueue = finishedQueue;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Toast t = finishedQueue.take();
				if (t.getId() != counter++
						|| t.getStatus() != Toast.Status.JAMMED) {
					System.out.println(">>>> Error : " + t);
					System.exit(0);
				} else {
					System.out.println("吃掉! " + t);
				}
			}
		} catch (Exception e) {
			System.out.println("吃的时候被中断");
		}
		System.out.println("吃完了~");
	}
}