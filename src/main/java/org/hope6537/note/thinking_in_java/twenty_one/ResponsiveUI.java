package org.hope6537.note.thinking_in_java.twenty_one;

import java.io.IOException;

/**
 * @describe 有响应的用户界面
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午3:44:09
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class ResponsiveUI extends Thread {
	// 多线程环境下的共享机制变量
	private volatile static double d = 1;

	public ResponsiveUI() {
		setDaemon(true);
		start();
	}

	@Override
	public void run() {
		while (d > 0) {
			d = d + (Math.PI + Math.E) / d;
		}
	}

	public static void main(String[] args) throws IOException {
		// 敲下回车发现的确是在后台运行
		new ResponsiveUI();
		System.in.read();
		System.out.println(d);
	}

}

/**
 * @describe 无响应的？F
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月26日下午3:45:27
 * @version 0.9
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
