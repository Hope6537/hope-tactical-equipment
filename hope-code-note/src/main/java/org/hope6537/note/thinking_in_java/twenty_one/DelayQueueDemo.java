package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 符合DelayQueue标准的对象需要实现两个接口
 * @signdate 2014年8月10日下午4:27:07
 * @company Changchun University&SHXT
 */
class DelayedTask implements Runnable, Delayed {
    protected static List<DelayedTask> sequence = new ArrayList<DelayedTask>();
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;

    public DelayedTask(int delta) {
        super();
        this.delta = delta;
        this.trigger = System.nanoTime()
                + TimeUnit.NANOSECONDS.convert(this.delta,
                TimeUnit.MILLISECONDS);
        sequence.add(this);
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if (trigger < that.trigger) {
            return -1;
        }
        if (trigger > that.trigger) {
            return 1;
        }
        return 0;
    }

    @Override
    public void run() {
        System.out.println(this + " ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public String summary() {
        return "(" + id + ":" + delta + ")";
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService exec;

        public EndSentinel(int delta, ExecutorService exec) {
            super(delta);
            this.exec = exec;
        }

        @Override
        public void run() {
            for (DelayedTask pt : sequence) {
                System.out.println(pt.summary() + " ");
            }
            System.out.println();
            System.out.println(this + " Calling ShutdownNow()");
            exec.shutdownNow();
        }

    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使优先队列里面装载的Delayed实现类线程执行
 * @signdate 2014年8月10日下午4:34:35
 * @company Changchun University&SHXT
 */
class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        super();
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                q.take().run();
            }
        } catch (Exception e) {
        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 延迟时间优先阻塞队列的应用 但是怎么使用的我还是有点蒙圈
 * @signdate 2014年8月10日下午4:26:42
 * @company Changchun University&SHXT
 */
public class DelayQueueDemo {

    public static void main(String[] args) {
        Random rand = new Random(47);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<DelayedTask>();
        for (int i = 0; i < 20; i++) {
            queue.put(new DelayedTask(rand.nextInt(5000)));
        }
        queue.add(new DelayedTask.EndSentinel(5000, exec));
        exec.execute(new DelayedTaskConsumer(queue));
    }

}
