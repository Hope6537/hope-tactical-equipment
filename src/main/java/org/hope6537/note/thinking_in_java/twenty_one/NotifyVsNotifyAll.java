package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Blocker {
    synchronized void waitingCall() {
        try {
            while (!Thread.interrupted()) {
                wait();
                System.out.println(Thread.currentThread() + "");
            }
        } catch (Exception e) {
            // Exit this way
        }
    }

    synchronized void prod() {
        notify();
    }

    synchronized void prodAll() {
        notifyAll();
    }
}

// 每个Task对象都会在blocker中阻塞

class Task implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

class Task2 implements Runnable {
    static Blocker blocker = new Blocker();

    @Override
    public void run() {
        blocker.waitingCall();
    }
}

public class NotifyVsNotifyAll {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Task());
        }
        exec.execute(new Task2());
        Timer timer = new Timer();
        // 所以每個run方法都經由激勵方法交替的調用喚醒和全部喚醒功能
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean prod = true;

            @Override
            public void run() {
                if (prod) {
                    System.out.println("notify()");
                    Task.blocker.prod();
                    prod = false;
                } else {
                    System.out.println("notifyAll()");
                    Task.blocker.prodAll();
                    prod = true;
                }
            }
        }, 400, 400);
        TimeUnit.SECONDS.sleep(5);
        timer.cancel();
        System.out.println("Timer Canceled");
        System.out.println("Task2.blokcer.prodAll()");
        Task2.blocker.prodAll();
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("Shutting Down");
        exec.shutdownNow();
    }
    // 在输出中我们看到就算是Task2被阻塞，也没有因为Task的唤醒或唤醒全部方法而将其唤醒
    // 因为唤醒方法仅仅会在当前锁对象的方式中作用

}
