package org.hope6537.note.thinking_in_java.twenty_one;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 一个重对象
 * @signdate 2014年8月10日下午7:18:37
 * @company Changchun University&SHXT
 */
public class Fat {

    private static int counter = 0;
    private final int id = counter++;
    private volatile double d;

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
