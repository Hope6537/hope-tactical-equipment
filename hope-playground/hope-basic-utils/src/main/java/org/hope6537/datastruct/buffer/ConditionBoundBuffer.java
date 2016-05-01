package org.hope6537.datastruct.buffer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hope6537 on 15/11/27.
 * Any Question sent to hope6537@qq.com
 */
public class ConditionBoundBuffer<V> extends BaseBoundedBuffer<V> {

    protected final Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    /**
     * using condition lock to put element into the buffer
     *
     * @param v element
     * @throws InterruptedException
     */
    public void put(V v) throws InterruptedException {
        lock.lock();
        try {
            //If the full condition append , it will blocking until the notFull
            while (isFull()) {
                notFull.await();
            }
            doPut(v);
            //notify the buffer is notEmpty
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * using condition lock to take element from the buffer
     *
     * @throws InterruptedException
     */
    public V take() throws InterruptedException {
        lock.lock();
        V v;
        try {
            //If the empty conditon append, it will blocking until the notEmpty
            while (isEmpty()) {
                notEmpty.await();
            }
            v = doTake();
            notFull.signal();
            return v;
        } finally {
            lock.unlock();
        }
    }

}
