package org.hope6537.note.tij.fifteen;

/**
 * @param <T>
 * @version 0.9
 * @Describe 一个持有对象类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:03:18
 * @company Changchun University&SHXT
 */
public class Holder<T> {

    private T value;

    public Holder() {

    }

    public Holder(T value) {
        super();
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return value.equals(obj);
    }
}

