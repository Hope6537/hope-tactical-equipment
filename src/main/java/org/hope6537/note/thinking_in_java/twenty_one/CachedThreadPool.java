package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe Executor执行器的使用 首选
 * @signdate 2014年7月25日下午6:54:36
 * @company Changchun University&SHXT
 */
public class CachedThreadPool {

    public static void main(String[] args) {
        // 定义一个执行器
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new LiftOff());
        }
        // 对shutdown的调用是用于防止新的任务提交给他
        exec.shutdown();
    }
}

// 但是要注意在线程池中 现有线程在有可能的情况下，都会被自动复用

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 有限个数线程池 上面的出现问题采用它
 * @signdate 2014年7月25日下午7:17:03
 * @company Changchun University&SHXT
 */
class FixedThreadPool {
    // 使用了有限的线程集来提交任务
    public static void main(String[] args) {
        // 定义个数
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            // 通过池中获取固定线程
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 单一线程池 用于执行连续运行的事务
 * @signdate 2014年7月25日下午7:17:53
 * @company Changchun University&SHXT
 */
class SingleThreadExecutor {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            // 通过池中获取固定线程 它内部有一个悬挂的任务队列
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
