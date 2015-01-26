package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 泛型数组实验2
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:41:46
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class GenericArray2<T> {

	private Object[] array;

	public GenericArray2(int size) {
		this.array = new Object[size];
	}

	public void put(int index, T item) {
		array[index] = item;
	}

	public T get(int index) {
		return (T) array[index];
	}

	public T[] rep() {
		return (T[]) array;
	}

	public static void main(String[] args) {
		GenericArray2<Integer> gai = new GenericArray2<Integer>(10);
		for (int i = 0; i < 10; i++) {
			gai.put(i, i);
		}
		for (int i = 0; i < 10; i++) {
			System.out.print(gai.get(i) + " ");
		}
		System.out.println();
		try{
			//但是还是会在预期产生异常
			Integer [] ia = gai.rep();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
