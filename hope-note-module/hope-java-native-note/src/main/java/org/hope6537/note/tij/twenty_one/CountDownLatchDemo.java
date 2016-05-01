package org.hope6537.note.tij.twenty_one;

//: concurrency/CountDownLatchDemo.java

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Performs some portion of a task:
class TaskPortion implements Runnable {
    private static int counter = 0;
    private static Random rand = new Random(47);
    private final int id = counter++;
    private final CountDownLatch latch;

    TaskPortion(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            // 工作的同时计数
            doWork();
            // countDown属于？倒计时？到0的时候才可以执行wait或挂起操作
            latch.countDown();
        } catch (InterruptedException ex) {
            // Acceptable way to exit
        }
    }

    public void doWork() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(2000));
        System.out.println(this + "completed");
    }

    public String toString() {
        return String.format("%1$-3d ", id);
    }
}

// Waits on the CountDownLatch:
class WaitingTask implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final CountDownLatch latch;

    WaitingTask(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        try {
            // 等待 只有这个操作会被阻塞 直到计数器到达0
            latch.await();
            // 操作被激活之后才输出
            System.out.println("Latch barrier passed for " + this);
        } catch (InterruptedException ex) {
            System.out.println(this + " interrupted");
        }
    }

    public String toString() {
        return String.format("WaitingTask %1$-3d ", id);
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 用来同步一个或多个任务，强制他们等待由其他任务执行的一组操作的完成
 * @signdate 2014年8月10日下午2:16:10
 * @company Changchun University&SHXT
 */
public class CountDownLatchDemo {
    static final int SIZE = 100;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        // All must share a single CountDownLatch object:
        CountDownLatch latch = new CountDownLatch(SIZE);
        for (int i = 0; i < 10; i++)
            exec.execute(new WaitingTask(latch));
        for (int i = 0; i < SIZE; i++)
            exec.execute(new TaskPortion(latch));
        System.out.println("Launched all tasks");
        exec.shutdown(); // Quit when all tasks complete
    }
}

