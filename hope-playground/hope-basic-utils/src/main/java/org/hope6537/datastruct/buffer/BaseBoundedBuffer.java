package org.hope6537.datastruct.buffer;

/**
 * Base Array Buffer [with limit]
 * Created by hope6537 on 15/11/27.
 * Any Question sent to hope6537@qq.com
 */
public class BaseBoundedBuffer<V> {

    private static final int DEFAULT_CAPACITY = 12;

    protected final V[] buf;
    protected int tail;
    protected int head;
    protected int count;

    public BaseBoundedBuffer() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public BaseBoundedBuffer(int capacity) {
        this.buf = (V[]) new Object[capacity];
    }

    public BaseBoundedBuffer(V[] buf) {
        this.buf = buf;
    }

    protected final void doPut(V v) {
        synchronized (this) {
            buf[tail] = v;
            if (++tail == buf.length) {
                tail = 0;
            }
            ++count;
        }
    }

    protected final V doTake() {
        synchronized (this) {
            V v = buf[head];
            buf[head] = null;
            if (++head == buf.length) {
                head = 0;
            }
            --count;
            return v;
        }
    }

    public synchronized final boolean isFull() {
        return count == buf.length;
    }

    public synchronized final boolean isEmpty() {
        return count == 0;
    }

}
