package org.hope6537.note.thinking_in_java.fifteen;

class Amphibian {
}

class Vehicle {
}

/**
 * @Describe 元组的使用
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:05:51
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class TupleTest {
	static final String A = "hi";
	static final Integer B = 24;

	static TwoTuple<String, Integer> f() {
		return new TwoTuple<String, Integer>(A, B);
	}

	static ThreeTuple<Amphibian, String, Integer> g() {
		return new ThreeTuple<Amphibian, String, Integer>(new Amphibian(), A, B);
	}

	static FourTuple<Vehicle, Amphibian, String, Integer> h() {
		return new FourTuple<Vehicle, Amphibian, String, Integer>(
				new Vehicle(), new Amphibian(), A, B);
	}

	static FiveTuple<Vehicle, Amphibian, String, Integer, Double> k() {
		return new FiveTuple<Vehicle, Amphibian, String, Integer, Double>(
				new Vehicle(), new Amphibian(), A, B, 97.2);
	}

	public static void main(String[] args) {
		TwoTuple<String, Integer> ttsi = f();
		System.out.println(ttsi);
		System.out.println(g());
		System.out.println(h());
		System.out.println(k());
	}
}

/**
 * @Describe 这是定义元组的一种方法
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午12:57:01
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <A>
 * @param <B>
 */
class TwoTuple<A, B> {
	public final A first;
	public final B second;

	public TwoTuple(A a, B b) {
		first = a;
		second = b;
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[first=" + first + ", second="
				+ second + "]";
	}
}

class ThreeTuple<A, B, C> extends TwoTuple<A, B> {
	public final C c;

	public ThreeTuple(A a, B b, C c) {
		super(a, b);
		this.c = c;
	}

	@Override
	public String toString() {

		return super.toString() + "ThreeTuple [c=" + c + "]";
	}
}

class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
	public final D d;

	public FourTuple(A a, B b, C c, D d) {
		super(a, b, c);
		this.d = d;
	}

	@Override
	public String toString() {
		return super.toString() + "FourTuple [d=" + d + "]";
	}

}

class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
	private E e;

	public FiveTuple(A a, B b, C c, D d, E e) {
		super(a, b, c, d);
		this.e = e;
	}

	@Override
	public String toString() {
		return super.toString() + "FiveTuple [e=" + e + "]";
	}

}
