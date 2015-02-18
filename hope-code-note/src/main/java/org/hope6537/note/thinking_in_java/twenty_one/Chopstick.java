package org.hope6537.note.thinking_in_java.twenty_one;

public class Chopstick {
    // 表示是被被占用
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        // 如果被占用，则挂起当前线程。
        while (taken)
            wait();
        // 和上面的循环无关，不被占用就被当前线程占用
        taken = true;
    }

    public synchronized void drop() {
        // 取消占用，同时激活当前锁所阻塞的所有线程，表示可以拿这个筷子了
        taken = false;
        notifyAll();
    }
}