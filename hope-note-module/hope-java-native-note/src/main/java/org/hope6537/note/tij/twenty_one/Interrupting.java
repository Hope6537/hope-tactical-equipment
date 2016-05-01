package org.hope6537.note.tij.twenty_one;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 睡眠阻塞
 * @signdate 2014年8月13日下午12:47:28
 * @company Changchun University&SHXT
 */
class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (Exception e) {
            System.out.println("Interrupting");
        }
        System.out.println("Exiting Sleep");
    }

}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe IO阻塞
 * @signdate 2014年8月13日下午12:47:36
 * @company Changchun University&SHXT
 */
class IOBlocked implements Runnable {
    private InputStream in;

    public IOBlocked(InputStream in) {
        this.in = in;

    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for Read");
            in.read();
        } catch (Exception e) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupting from io");
            } else {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Exiting IO");
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 死锁阻塞
 * @signdate 2014年8月13日下午12:47:42
 * @company Changchun University&SHXT
 */
class SynchronizedBlocked implements Runnable {
    public SynchronizedBlocked() {
        new Thread() {
            public void run() {
                f();// 被这个线程死锁
            }
        }.start();
    }

    // 这个方法永远不会放掉锁
    public synchronized void f() {
        while (true)
            Thread.yield();
    }

    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}

public class Interrupting {

    private static ExecutorService exec = Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        // 执行exec中的单个线程 同时获取到该线程的可操作的Future对象
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        // 通过这个持有对象进行单个线程的终止操作
        f.cancel(true);
        System.out.println("Interrupt sent to" + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        System.out.println("----------------");
        test(new SynchronizedBlocked());
        System.out.println("----------------");
        test(new IOBlocked(System.in));
        TimeUnit.SECONDS.sleep(3);
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }

	/* 根据输出我们可以看到 我们能够直接中断sleep的阻塞线程 但是没有办法中断I/O阻塞和死锁阻塞？那可咋办呢？
     * 对于这类问题 可以进行关闭任务再其上发生阻塞的底层资源
	 * Interrupting org.hope6537.note.tij.twenty_one.SleepBlocked
	 * Interrupt sent toorg.hope6537.note.tij.twenty_one.SleepBlocked
	 * ---------------- Interrupting Exiting Sleep Trying to call f()
	 * Interrupting org.hope6537.note.tij.twenty_one.SynchronizedBlocked
	 * Interrupt sent
	 * toorg.hope6537.note.tij.twenty_one.SynchronizedBlocked
	 * ---------------- Waiting for Read Interrupting
	 * org.hope6537.note.tij.twenty_one.IOBlocked Interrupt sent
	 * toorg.hope6537.note.tij.twenty_one.IOBlocked Aborting with
	 * System.exit(0)
	 */
}
