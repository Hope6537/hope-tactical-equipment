package org.hope6537.note.thinking_in_java.twenty_one;

//{Args: 0 5 timeout}

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DiningPhilosophers {

    public static void deadLockingRun(String[] args) throws Exception {

        int ponder = 5;
        if (args.length > 0)
            ponder = Integer.parseInt(args[0]);
        int size = 5;
        if (args.length > 1)
            size = Integer.parseInt(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];
        for (int i = 0; i < size; i++)
            sticks[i] = new Chopstick();
        for (int i = 0; i < size; i++)
            // 得到他手边的左手的和右手的筷子的引用
            // 他们都试图先获得右边的筷子，然后再获取左边的筷子
            exec.execute(new Philosopher(sticks[i], sticks[(i + 1) % size], i,
                    ponder));
        if (args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(5);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();

    }

    public static void fixedLockingRun(String[] args) throws Exception {
        int ponder = 5;
        if (args.length > 0)
            ponder = Integer.parseInt(args[0]);
        int size = 5;
        if (args.length > 1)
            size = Integer.parseInt(args[1]);
        ExecutorService exec = Executors.newCachedThreadPool();
        Chopstick[] sticks = new Chopstick[size];
        for (int i = 0; i < size; i++)
            sticks[i] = new Chopstick();
        for (int i = 0; i < size; i++) {
            // 通过取消循环等待来解除死锁
            if (i < (size - 1))
                exec.execute(new Philosopher(sticks[i], sticks[i + 1], i,
                        ponder));
            else
                // 改变取筷子方式
                exec.execute(new Philosopher(sticks[0], sticks[i], i, ponder));
        }
        if (args.length == 3 && args[2].equals("timeout"))
            TimeUnit.SECONDS.sleep(2);
        else {
            System.out.println("Press 'Enter' to quit");
            System.in.read();
        }
        exec.shutdownNow();
    }

    public static void main(String[] args) throws Exception {
        // 0思考时间
        deadLockingRun(new String[]{"0", "5", "timeout"});
        //fixedLockingRun(new String[] { "0", "5", "timeout" });
    }

	/*
	 * 
	 */
}
