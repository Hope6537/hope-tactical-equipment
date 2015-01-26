package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 一个普通的持有类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午06:05:07
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class BasicHolder<T> {
	T element;
	public void setElement(T element) {
		this.element = element;
	}
	public T getElement() {
		return element;
	}
	void f(){
		System.out.println(element.getClass().getSimpleName());
	}
}


