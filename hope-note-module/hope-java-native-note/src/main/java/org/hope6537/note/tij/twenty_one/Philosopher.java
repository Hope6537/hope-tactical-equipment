package org.hope6537.note.tij.twenty_one;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Philosopher implements Runnable {
    private final int id;
    /**
     * @describe 思考的时间长度
     */
    private final int ponderFactor;
    private Chopstick left;
    private Chopstick right;
    private Random rand = new Random(47);

    public Philosopher(Chopstick left, Chopstick right, int ident, int ponder) {
        this.left = left;
        this.right = right;
        id = ident;
        ponderFactor = ponder;
    }

    private void pause() throws InterruptedException {
        if (ponderFactor == 0)
            // 如果为零，取消思考流程 ? 或者是归结于状态?
            return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.println(this + " " + "思考");
                pause();
                // Philosopher becomes hungry
                System.out.println(this + " " + "拿起了右边的筷子");
                right.take();
                System.out.println(this + " " + "拿起了左边的筷子");
                left.take();
                System.out.println(this + " " + "在吃饭");
                pause();
                //System.out.println(this + " " + "放下了筷子");
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " " + "被中断");
        }
    }

    public String toString() {
        return "哲学家 " + id + "号 ";
    }
}
