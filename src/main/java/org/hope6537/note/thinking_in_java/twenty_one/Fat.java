package org.hope6537.note.thinking_in_java.twenty_one;

/**
 * @describe 一个重对象
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月10日下午7:18:37
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Fat {

	private volatile double d;
	private static int counter = 0;
	private final int id = counter++;

	public Fat() {
		for (int i = 1; i < 10000; i++) {
			d += (Math.PI + Math.E) / (double) i;
		}
	}

	public void operation() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "Fat [id=" + id + "]";
	}

}
