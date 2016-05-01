package org.hope6537.note.tij.twenty_one;

import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 后台线程实验
 * @signdate 2014年7月26日上午9:58:15
 * @company Changchun University&SHXT
 */
public class SimpleDaemons implements Runnable {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Thread daemon = new Thread(new SimpleDaemons());
            daemon.setDaemon(true);
            daemon.start();
        }
        System.out.println("All Daemons Started");
        TimeUnit.MILLISECONDS.sleep(200);
        // 一旦睡眠时间结束，主线程终止，那么后台线程也将被迫终止
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + " : " + this);
            }
        } catch (Exception e) {
            System.out.println("Sleeping");
        }
    }
}
/*
 * All Daemons Started Thread[Thread-2,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@33b7b32c
 * Thread[Thread-1,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6154283a
 * Thread[Thread-5,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6154283a
 * Thread[Thread-8,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@64726693
 * Thread[Thread-3,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6c0e9e40
 * Thread[Thread-9,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@12ac706a
 * Thread[Thread-0,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@2b571dff
 * Thread[Thread-7,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@565dd915
 * Thread[Thread-4,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@7ea06d25
 * Thread[Thread-6,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@5c1d29c1
 * Thread[Thread-5,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6154283a
 * Thread[Thread-8,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@64726693
 * Thread[Thread-0,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@2b571dff
 * Thread[Thread-7,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@565dd915
 * Thread[Thread-6,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@5c1d29c1
 * Thread[Thread-3,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6c0e9e40
 * Thread[Thread-4,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@7ea06d25
 * Thread[Thread-1,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@6154283a
 * Thread[Thread-2,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@33b7b32c
 * Thread[Thread-9,5,main] :
 * org.hope6537.note.tij.twenty_one.SimpleDaemons@12ac706a
 */

