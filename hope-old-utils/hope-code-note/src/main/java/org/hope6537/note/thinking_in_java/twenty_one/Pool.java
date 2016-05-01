package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @param <T>
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 通过许可证方式建立的对象池
 * @signdate 2014年8月10日下午7:04:59
 * @company Changchun University&SHXT
 */
public class Pool<T> {

    private int size;
    private List<T> items = new ArrayList<T>();
    // 这个布尔数组将会追踪迁出的对象
    private volatile boolean[] checkedOut;
    // 这是啥？ 许可证，能够允许n个任务同时访问这个资源
    private Semaphore available;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);
        for (int i = 0; i < size; ++i) {
            try {
                items.add(classObject.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public T checkOut() throws InterruptedException {
        available.acquire();
        // 声明给出一个对象，同时返回值中的确迁出一个对象
        // 如果当前对象池没有对象可用了，那么就会阻塞当前调用进程，知道有对象返回并可用
        return getItem();
    }

    private synchronized T getItem() {
        for (int i = 0; i < size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }

    private synchronized boolean releaseItem(T item) {
        int index = items.indexOf(item);
        if (index == -1) {
            return false;
        }
        if (checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false;
    }

    public void checkIn(T x) {
        if (releaseItem(x)) {
            // 将作为参数的这个对象收回
            available.release();
        }
    }
}
