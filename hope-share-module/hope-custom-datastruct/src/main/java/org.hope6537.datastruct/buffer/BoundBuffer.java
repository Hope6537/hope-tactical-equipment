package org.hope6537.datastruct.buffer;

import java.util.concurrent.locks.Lock;

/**
 * Condition Buffer based BaseBuffer
 * Created by hope6537 on 15/11/27.
 * Any Question sent to hope6537@qq.com
 */
public class BoundBuffer<V> extends BaseBoundedBuffer<V> {

    /**
     * state judge on taken
     */
    private static final int WILL_TAKE = 0;
    /**
     * state judge on put
     */
    private static final int WILL_PUT = 1;

    /**
     * state judge lock
     */
    private Lock conditionPredicateLock;

    public BoundBuffer(int capacity) {
        super(capacity);
    }

    public synchronized final boolean notNull() {
        return !this.isFull();
    }

    public synchronized final boolean notEmpty() {
        return !this.isEmpty();
    }

    /**
     * State dependent judge method , when the thread has been notify , it will check the conditions.
     * If not true , will throw new exception.
     *
     * @throws InterruptedException
     */
    void stateDependentMethod(int conditions) throws InterruptedException {
        synchronized (conditionPredicateLock) {
            while (!conditionPredicate(conditions)) {
                conditionPredicateLock.wait();
            }
            //default enable state
        }
    }

    /**
     * get condition predicate
     * BoundBuffer.WILL_PUT
     * BoundedBuffer.WILL_TAKE
     *
     * @param conditions condition types
     * @return
     */
    private synchronized boolean conditionPredicate(int conditions) {
        switch (conditions) {
            case WILL_PUT:
                return notNull();
            case WILL_TAKE:
                return notEmpty();
            default:
                throw new IllegalStateException();
        }
    }


    /**
     * Blocking until the buffer has size to put the new element
     *
     * @param v element
     */
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        boolean wasEmpty = isEmpty();
        doPut(v);
        //using strategy on condition notify
        if (wasEmpty) {
            notifyAll();
        }
    }

    /**
     * Blocking until the buffer has a element and return to callers
     *
     * @return element in buffer's head
     * @throws InterruptedException
     */
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        V v = doTake();
        notifyAll();
        return v;
    }

}
