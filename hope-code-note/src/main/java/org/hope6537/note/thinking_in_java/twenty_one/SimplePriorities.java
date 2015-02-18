package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 線程優先級實驗
 * @signdate 2014年7月26日上午9:51:30
 * @company Changchun University&SHXT
 */
public class SimplePriorities implements Runnable {
    private static int countDown = 5;
    private volatile double d;
    private int priority;

    public SimplePriorities(int priority) {
        super();
        this.priority = priority;
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new SimplePriorities(Thread.MIN_PRIORITY));
        }
        // 最好只使用这两种
        exec.execute(new SimplePriorities(Thread.MAX_PRIORITY));
        exec.shutdown();
    }

    @Override
    public String toString() {
        return Thread.currentThread() + ":" + countDown;
    }

    @Override
    public void run() {
        // 优先级在线程执行时被加载 不在构造方法
        Thread.currentThread().setPriority(priority);
        while (true) {
            for (int i = 1; i < 100000; i++) {
                d += (Math.PI + Math.E) / (double) i;
                if (i % 1000 == 0) {
                    Thread.yield();
                }
            }
            System.out.println(this);
            if (--countDown == 0) {
                return;
            }
        }
    }
}

/*
 * Thread[pool-1-thread-4,1,main]:-22134 Thread[pool-1-thread-1,1,main]:-22135
 * Thread[pool-1-thread-2,1,main]:-22136 Thread[pool-1-thread-5,1,main]:-22137
 * Thread[pool-1-thread-3,1,main]:-22138 Thread[pool-1-thread-4,1,main]:-22139
 * Thread[pool-1-thread-1,1,main]:-22140 Thread[pool-1-thread-3,1,main]:-22141
 * Thread[pool-1-thread-5,1,main]:-22142 Thread[pool-1-thread-4,1,main]:-22143
 * Thread[pool-1-thread-2,1,main]:-22144 Thread[pool-1-thread-1,1,main]:-22145
 * Thread[pool-1-thread-3,1,main]:-22146 Thread[pool-1-thread-4,1,main]:-22147
 * Thread[pool-1-thread-5,1,main]:-22148 Thread[pool-1-thread-2,1,main]:-22149
 * Thread[pool-1-thread-1,1,main]:-22150 Thread[pool-1-thread-3,1,main]:-22151
 * Thread[pool-1-thread-2,1,main]:-22152 Thread[pool-1-thread-4,1,main]:-22153
 * Thread[pool-1-thread-1,1,main]:-22154 Thread[pool-1-thread-3,1,main]:-22155
 * Thread[pool-1-thread-5,1,main]:-22156 Thread[pool-1-thread-2,1,main]:-22157
 * Thread[pool-1-thread-1,1,main]:-22158 Thread[pool-1-thread-3,1,main]:-22159
 * Thread[pool-1-thread-5,1,main]:-22160 Thread[pool-1-thread-4,1,main]:-22161
 * Thread[pool-1-thread-2,1,main]:-22162 Thread[pool-1-thread-5,1,main]:-22163
 * Thread[pool-1-thread-4,1,main]:-22164 Thread[pool-1-thread-1,1,main]:-22165
 * Thread[pool-1-thread-3,1,main]:-22166 Thread[pool-1-thread-2,1,main]:-22167
 */
