package org.hope6537.note.tij.twenty_one;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Interrupting2 {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Blocked2());
        thread.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt");
        // 和I/O不同，interrupt是可以打断被互斥所阻塞的调用
        thread.interrupt();
    }
}

class BlockedMutex {
    // 这种锁具备中断在其上被阻塞的任务的功能
    private Lock lock = new ReentrantLock();

    public BlockedMutex() {
        // 他要获取所创建对象上自身的Lock 并且从不释放这个锁
        lock.lock();
    }

    /**
     * @descirbe 所以如果我们试图从第二个任务中调用f方法，不同于创建BlockedMutex这个任务，那麼就會因為對象不可獲得而阻塞
     * @author Hope6537(赵鹏)
     * @signDate 2014年8月9日上午10:03:01
     * @version 0.9
     */
    public void f() {
        try {
            lock.lockInterruptibly();
            System.out.println("lock acquired in f()");
        } catch (Exception e) {
            System.out.println("Interrupting from lock acquisition in f()");
        }
    }
}

class Blocked2 implements Runnable {
    BlockedMutex blockedMutex = new BlockedMutex();

    @Override
    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        // 方法会在这里停止 因为f不可被访问
        blockedMutex.f();
        System.out.println("Broken out of blocked call");
    }

}