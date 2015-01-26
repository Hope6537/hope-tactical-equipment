package org.hope6537.note.thinking_in_java.fifteen;

class SelfBounded1<T extends SelfBounded1<T>> {
	T element;

	public SelfBounded1<T> set(T element) {
		this.element = element;
		return this;
	}

	public T get() {
		return element;
	}
}

class A extends SelfBounded1<A> {

}

class B extends SelfBounded1<A> {

}

class C extends SelfBounded1<C> {
	C setAndGet(C arg) {
		set(arg);
		return get();
	}
}

class D {

}

class E extends SelfBounded1 {

}

/**
 * @Describe 强制泛型当做自己的边界参数来使用
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-20下午12:13:43
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class SelfBounding {
	public static void main(String[] args) {
		A a = new A();
		a.set(new A());
		a = a.set(new A()).get();
		a = a.get();
		C c = new C();
		c = c.setAndGet(new C());
	}
}
/*class E extends SelfBounded1<D>{
	会出现编译错误
}*/



