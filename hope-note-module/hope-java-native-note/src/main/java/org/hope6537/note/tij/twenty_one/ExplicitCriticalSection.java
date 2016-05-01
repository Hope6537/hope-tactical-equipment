package org.hope6537.note.tij.twenty_one;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使用显式的锁来创建临界区
 * @signdate 2014年8月7日下午5:07:54
 * @company Changchun University&SHXT
 */
public class ExplicitCriticalSection {
    public static void main(String[] args) {
        CriticalSection.testApproaches(new ExplicitPairManager1(),
                new ExplicitPairManager2());
    }
}

class ExplicitPairManager1 extends PairManager {
    private Lock lock = new ReentrantLock();

    public synchronized void increment() {
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            store(getPair());
        } finally {
            lock.unlock();
        }
    }

}

class ExplicitPairManager2 extends PairManager {
    private Lock lock = new ReentrantLock();

    public void increment() {
        Pair temp;
        lock.lock();
        try {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        } finally {
            lock.unlock();
        }
        store(temp);
    }
}