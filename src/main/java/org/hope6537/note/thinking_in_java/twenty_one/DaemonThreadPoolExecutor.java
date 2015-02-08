package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 每个静态的ExecutorService创建方法都被重载为接受一个Factory对象，而这个对象将会被用来创建新的线程
 * @signdate 2014年7月26日上午11:06:13
 * @company Changchun University&SHXT
 */
public class DaemonThreadPoolExecutor extends ThreadPoolExecutor {
    public DaemonThreadPoolExecutor() {
        // 这么一长串子的构造方法都是用来干啥滴腻？
        // 重载了执行线程池的方法？然后呢？顶一个DaemonThreadFactory产生的对象是线程池的唯一对象?
        super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new DaemonThreadFactory());

    }
}
