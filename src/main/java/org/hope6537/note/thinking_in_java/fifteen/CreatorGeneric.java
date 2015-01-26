package org.hope6537.note.thinking_in_java.fifteen;

abstract class GenericWithCreate<T> {
	final T element;

	GenericWithCreate() {
		element = create();
	}

	abstract T create();
}

class X {
}

class Creator extends GenericWithCreate<X> {
	@Override
	X create() {
		return new X();
	}

	void f() {
		System.out.println(element.getClass().getSimpleName());
	}
}

/**
 * @Describe 模板方法设计模式
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:25:31
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class CreatorGeneric {
	public static void main(String[] args) {
		Creator c = new Creator();
		c.f();
	}
}
