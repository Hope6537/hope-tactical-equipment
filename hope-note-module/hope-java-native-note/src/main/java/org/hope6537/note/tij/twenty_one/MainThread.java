package org.hope6537.note.tij.twenty_one;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 运行刚才的定义的任务
 * @signdate 2014年7月25日下午6:33:29
 * @company Changchun University&SHXT
 */
public class MainThread {
    public static void main(String[] args) {
        LiftOff launch = new LiftOff();
        launch.run();
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 线程的标准驱动方式
 * @signdate 2014年7月25日下午6:35:55
 * @company Changchun University&SHXT
 */
class BasicThreads {
    public static void main(String[] args) {
        Thread t = new Thread(new LiftOff());
        t.start();
        // start迅速就返回了 所以我们可以看到下面的输出语句先于线程执行
        System.out.println("Waiting for LiftOff");
    }
}

class MoreBasicThreads {
    public static void main(String[] args) {
        // 分发线程 因为线程调度机制是不确定的 所以可能会导致输出不同
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new LiftOff());
            t.start();
        }
        System.out.println("5 Rockets are ready to launch");
    }
}
