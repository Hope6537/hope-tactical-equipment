package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

class Horse implements Runnable {
    private static int counter = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;
    private final int id = counter++;
    private int strides = 0;

    public Horse(CyclicBarrier b) {
        barrier = b;
    }

    public Horse(int strides) {
        super();
        this.strides = strides;
    }

    public synchronized int getStrides() {
        return strides;
    }

    @Override
    public void run() {
        try {
            // 这一次的动作相当于这一秒他跑了多远
            while (!Thread.interrupted()) {
                synchronized (this) {
                    // 给出一个0 1 2的随机数 然后加到当前里程数里
                    strides += rand.nextInt(3);
                }
                // 在其他的完成之前等待
                barrier.await();
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e1) {
            throw new RuntimeException(e1);
        }

    }

    @Override
    public String toString() {
        return "Horse [id=" + id + ", strides=" + strides + "]";
    }

    /**
     * @return
     * @descirbe 奔跑
     * @author Hope6537(赵鹏)
     * @signDate 2014年8月10日下午3:39:43
     * @version 0.9
     */
    public String tracks() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < getStrides(); i++) {
            s.append("-");
        }
        s.append(id);
        return s.toString();
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe CyclicBarrier的Demo应用 赛马仿真程序
 * @signdate 2014年8月10日下午3:24:25
 * @company Changchun University&SHXT
 */
public class HorseRace {

    static final int FINISH_LINE = 75;

    private List<Horse> horses = new ArrayList<Horse>();

    private ExecutorService exec = Executors.newCachedThreadPool();
    // 工作机制 创建一组任务，并行的执行工作，然后在进行下一个步骤之前等待
    private CyclicBarrier barrier;

    public HorseRace(int nHorses, final int pause) {
        barrier = new CyclicBarrier(nHorses, new Runnable() {

            @Override
            public void run() {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++) {
                    s.append("X");
                }
                System.out.println(s);
                for (Horse horse : horses) {
                    System.out.println(horse.tracks());
                }
                for (Horse horse : horses) {
                    if (horse.getStrides() >= FINISH_LINE) {
                        System.out.println(horse + " won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(pause);
                } catch (Exception e) {
                    System.out.println("动作睡眠被打断");
                }
            }
        });
        for (int i = 0; i < nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    public static void main(String[] args) {
        int nHorses = 7;
        int pause = 200;
        if (args.length > 0) { // Optional argument
            int n = new Integer(args[0]);
            nHorses = n > 0 ? n : nHorses;
        }
        if (args.length > 1) { // Optional argument
            int p = new Integer(args[1]);
            pause = p > -1 ? p : pause;
        }
        new HorseRace(nHorses, pause);
    }
}
