package org.hope6537.note.thinking_in_java.twenty_one;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 被互斥所阻塞
 * @signdate 2014年8月9日上午9:44:10
 * @company Changchun University&SHXT
 */
public class MultiLock {

    public static void main(String[] args) {
        final MultiLock lock = new MultiLock();
        new Thread() {
            // 关于这个锁的叙述 他在第一次調用的時候就獲取了multilock這個锁 因此用一个任务将会在f2()的调用中再次获取这个锁
            // 因此一个任务应该能够调用同一个对象的其他synchronized方法
            public void run() {
                lock.f1(10);
            }

            ;
        }.start();
    }

    public synchronized void f1(int count) {
        if (count-- > 0) {
            System.out.println("f1() calling f2() with count " + count);
            f2(count);
        }
    }

    public synchronized void f2(int count) {
        if (count-- > 0) {
            System.out.println("f2() calling f1() with count " + count);
            f1(count);
        }
    }
}
