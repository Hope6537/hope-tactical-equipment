package org.hope6537.note.tij.twenty_one;

import java.util.concurrent.ThreadFactory;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 后台线程工厂 可以传递给执行器来执行
 * @signdate 2014年7月26日上午10:58:45
 * @company Changchun University&SHXT
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    }
}
