package org.hope6537.note.thinking_in_java.twenty_one;

import java.io.IOException;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 有响应的用户界面
 * @signdate 2014年7月26日下午3:44:09
 * @company Changchun University&SHXT
 */
public class ResponsiveUI extends Thread {
    // 多线程环境下的共享机制变量
    private volatile static double d = 1;

    public ResponsiveUI() {
        setDaemon(true);
        start();
    }

    public static void main(String[] args) throws IOException {
        // 敲下回车发现的确是在后台运行
        new ResponsiveUI();
        System.in.read();
        System.out.println(d);
    }

    @Override
    public void run() {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }
    }

}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 无响应的？F
 * @signdate 2014年7月26日下午3:45:27
 * @company Changchun University&SHXT
 */
class UnresponsiveUI {
    // 多线程环境下的共享机制变量
    private volatile double d = 1;

    public UnresponsiveUI() throws Exception {
        while (d > 0) {
            d = d + (Math.PI + Math.E) / d;
        }
        System.in.read();
    }
}
