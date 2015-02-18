package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

abstract class IntGenerator {
    // 使用布尔型来保证原子性
    // 使用volatile关键字来保证可视性
    private volatile boolean canceled = false;

    public abstract int next();

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return canceled;
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 不正确的共享资源竞争
 * @signdate 2014年7月26日下午5:35:59
 * @company Changchun University&SHXT
 */
public class EvenChecker implements Runnable {
    private final int id;
    private IntGenerator generator;

    public EvenChecker(IntGenerator generator, int id) {
        super();
        this.generator = generator;
        this.id = id;
    }

    public static void test(IntGenerator gp, int count) {
        // 在这里启用多个线程同时对这个生成器对象的Int值进行next操作
        System.out.println("Press Control-C "
                + "to Exit");
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            // 所以创建了很多個EvenCheck对象
            exec.execute(new EvenChecker(gp, i));
        }
        exec.shutdown();
    }

    public static void test(IntGenerator gp) {
        test(gp, 10);
    }

    public static void main(String[] args) {
    }

    @Override
    public void run() {
        while (!generator.isCanceled()) {
            int val = generator.next();
            if (val % 2 != 0) {
                System.out.println(val + " not even!");
                generator.cancel();
            }
        }
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 具体实现生成器next方法的方法
 * @signdate 2014年7月26日下午5:31:31
 * @company Changchun University&SHXT
 */
class EvenGenerator extends IntGenerator {

    private int currentEvenValue = 0;

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator());
    }

    @Override
    public int next() {
        // 我们可以看到递增是根据两次自增长所确定的
        // 但是一个线程很有可能在另一个任务执行第一个对值的底层操作之后，第二个操作之前，调用next方法
        // 这将会使值处在一个不恰当的状态 在本例子中，即非偶数
        ++currentEvenValue;
        ++currentEvenValue;
        // 递增也不是原子性的操作，如果我们没有对任务进行保护，那么单一的递增也不是安全的
        return currentEvenValue;
    }

}
