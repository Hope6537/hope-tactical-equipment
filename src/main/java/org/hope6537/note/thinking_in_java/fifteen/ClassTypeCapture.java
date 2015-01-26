package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 类型查找
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:20:54
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class ClassTypeCapture<T> {

	Class<T> kind;

	public ClassTypeCapture(Class<T> kind) {
		super();
		this.kind = kind;
	}
	public boolean f(Object arg){
		return kind.isInstance(arg);
	}
}

