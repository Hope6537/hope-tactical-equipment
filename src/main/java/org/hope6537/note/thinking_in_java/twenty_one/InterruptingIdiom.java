package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.TimeUnit;

class NeedsCleanup {
    private final int id;

    public NeedsCleanup(int ident) {
        id = ident;
        // NeedsCleanup是代表强调你经由异常离开循环时，正确清理资源的必要性
        System.out.println("NeedsCleanup " + id);
    }

    public void cleanup() {
        System.out.println("Cleaning up " + id);
    }
}

class Blocked3 implements Runnable {
    private volatile double d = 0.0;

    public void run() {
        try {
            while (!Thread.interrupted()) {
                // point1
                NeedsCleanup n1 = new NeedsCleanup(1);
                // 所以所有创建的NeedsCleanup资源后面都紧跟着try-finally语句 以确保cleanup方法总是会被调用
                // Start try-finally immediately after definition
                // of n1, to guarantee proper cleanup of n1:
                try {
                    System.out.println("Sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    // point2
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    // Guarantee proper cleanup of n2:
                    try {
                        System.out.println("Calculating");
                        // A time-consuming, non-blocking operation:
                        for (int i = 1; i < 2500000; i++)
                            d = d + (Math.PI + Math.E) / d;
                        System.out.println("Finished time-consuming operation");
                    } finally {
                        n2.cleanup();
                    }
                } finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        } catch (InterruptedException e) {
            System.out.println("Exiting via InterruptedException");
        }
    }
}

public class InterruptingIdiom {

    /**
     * @param args
     * @throws NumberFormatException
     * @throws InterruptedException
     * @descirbe 通过给程序一个命令参数 表示在他调用中断之前以毫秒为单位的延时时间 通过不同的延迟，我们可以在不同的地点退出run
     * @author Hope6537(赵鹏)
     * @signDate 2014年8月9日上午10:42:55
     * @version 0.9
     */
    public static void run(String[] args) throws NumberFormatException,
            InterruptedException {

        if (args.length != 1) {
            System.out.println("usage: java InterruptingIdiom delay-in-mS");
            System.exit(1);
        }
        Thread t = new Thread(new Blocked3());
        t.start();
        TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
        t.interrupt();

    }

    public static void main(String[] args) throws Exception {
        run(new String[]{"1100"});
    }
}