package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {
        final Pool<Fat> pool = new Pool<Fat>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new CheckOutTask<Fat>(pool));
        }
        System.out.println("All CheckoutTasks Created");
        // 一个装载fat对象的表
        List<Fat> list = new ArrayList<Fat>();
        for (int i = 0; i < SIZE; i++) {
            Fat f = pool.checkOut();
            System.out.println(i + ": main() thread checked out");
            // 打印自己并装载到表中
            f.operation();
            list.add(f);
        }
        // 又出现了，单个线程的引用
        Future<?> blocked = exec.submit(new Runnable() {
            public void run() {
                try {
                    // 在对象池没有对象的情况下，该签出将被阻塞
                    pool.checkOut();
                } catch (Exception e) {
                    System.out.println("checkout() Interrupted");
                }
            }
        });
        TimeUnit.SECONDS.sleep(2);
        // 线程终止
        blocked.cancel(true);
        for (Fat f : list) {
            pool.checkIn(f);
        }
        // 将会忽视第二次插入
        for (Fat f : list) {
            pool.checkIn(f);
        }
        exec.shutdown();
    }

}

class CheckOutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckOutTask(Pool<T> pool) {
        super();
        this.pool = pool;
    }

    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + "checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + "checked in " + item);
            pool.checkIn(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "CheckOutTask [id=" + id + "]";
    }
}