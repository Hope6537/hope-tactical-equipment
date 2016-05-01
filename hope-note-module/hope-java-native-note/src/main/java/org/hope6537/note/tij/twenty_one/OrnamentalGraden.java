package org.hope6537.note.tij.twenty_one;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 仿真花园类
 * @signdate 2014年8月7日下午7:13:06
 * @company Changchun University&SHXT
 */
public class OrnamentalGraden {

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Entrance(i));
        }
        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();
        //如果在250毫秒之内线程没有全部完全终止，那么输出
        if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {
            System.out.println("Some tasks were not terminated!");
        }
        System.out.println("Total :" + Entrance.getTotalCount());
        System.out.println("Sum of Entrances: " + Entrance.sumEntrances());
    }

}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使用单个对象来跟踪花园参观者的主计数值
 * @signdate 2014年8月7日下午7:25:10
 * @company Changchun University&SHXT
 */
class Count {
    private int count = 0;
    private Random rand = new Random(47);

    /*
     * increment和 value方法都是上锁的 用来控制对count域的访问
     */
    public synchronized int increment() {
        // 使用random对象的方式来进行数字推送
        // 如果去掉锁，程序就会立即崩溃
        int temp = count;
        if (rand.nextBoolean()) {
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int value() {
        return count;
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe Count对象作为本类的一个静态域来存储
 * @signdate 2014年8月7日下午8:06:44
 * @company Changchun University&SHXT
 */
class Entrance implements Runnable {
    private static Count count = new Count();
    private static List<Entrance> entrances = new ArrayList<Entrance>();
    // 它只会被读取和赋值 可以安全的操作
    private static volatile boolean canceled = false;
    private final int id;
    /**
     * @describe 待维护的本地值
     */
    // 每个对象都在维护这个本地值 包含通过某个特定入口进入参观者的数量 提供了对count的检查
    private int number = 0;

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    public static void cancel() {
        canceled = true;
    }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance entrance : entrances) {
            sum += entrance.getValue();
        }
        return sum;
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                ++number;
            }
            System.out.println(this + " Total:" + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                System.out.println("Sleep interrupted");
            }
        }
        System.out.println("Stopping" + this);
    }

    public synchronized int getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "Entrance [id=" + id + "] " + getValue();
    }
}
