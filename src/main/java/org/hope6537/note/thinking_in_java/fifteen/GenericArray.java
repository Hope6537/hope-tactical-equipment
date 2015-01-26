package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 数组泛型实验
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:46:00
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class GenericArray<T> {

	private T[] array;

	public GenericArray(int sz) {
		// 首先定义一个自主擦除数组，然后自己将其强制类型转换
		// 但是无效，还是会在编译期间擦除
		array = (T[]) new Object[sz];
	}

	public void put(int index, T item) {
		array[index] = item;
	}

	public T get(int index) {
		return array[index];
	}

	public T[] rep() {
		return array;
	}
	
	public static void main(String[] args) {
		GenericArray<Integer> gai = new GenericArray<Integer>(10);
		//会出现编译异常 ClassCastException:
		Integer [] ia = gai.rep();
		//不会出现编译异常
		Object [] oa = gai.rep();
	}
}
