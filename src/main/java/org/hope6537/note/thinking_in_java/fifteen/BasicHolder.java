package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @param <T>
 * @version 0.9
 * @Describe 一个普通的持有类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:05:07
 * @company Changchun University&SHXT
 */
public class BasicHolder<T> {
    T element;

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    void f() {
        System.out.println(element.getClass().getSimpleName());
    }
}


