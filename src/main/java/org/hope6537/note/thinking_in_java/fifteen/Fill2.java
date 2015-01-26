package org.hope6537.note.thinking_in_java.fifteen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

interface Addable<T> {
	void add(T t);
}

public class Fill2 {
	
	public static void main(String[] args) {
		Fill2Test.main(args);
	}

	public static <T> void fill(Addable<T> addable,
			Class<? extends T> classToken, int size) {
		for (int i = 0; i < size; i++) {
			try {
				addable.add(classToken.newInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static <T> void fill(Addable<T> addable, Generator<T> generator,
			int size) {
		for (int i = 0; i < size; i++) {
			addable.add(generator.next());
		}
	}

}

class AddableCollectionAdapter<T> implements Addable<T> {
	private Collection<T> c;

	public AddableCollectionAdapter(Collection<T> c) {
		super();
		this.c = c;
	}

	public void add(T t) {
		c.add(t);
	};
}

class Adapter {
	public static <T> Addable<T> collectionAdapter(Collection<T> c) {
		return new AddableCollectionAdapter<T>(c);
	}
}

class AddableSimpleQueue<T> extends SimpleQueue<T> implements Addable<T> {
	public void add(T item) {
		super.add(item);
	}
}

class Fill2Test {
	public static void main(String[] args) {
		List<Coffee> carrier = new ArrayList<Coffee>();
		Fill2.fill(new AddableCollectionAdapter<Coffee>(carrier), Coffee.class,
				3);
		Fill2.fill(Adapter.collectionAdapter(carrier), Latte.class, 3);
		for(Coffee c : carrier){
			System.out.println(c);
		}
		System.out.println("-------------------------------");
		AddableSimpleQueue<Coffee> coffeeQueue = new AddableSimpleQueue<Coffee>();
		Fill2.fill(coffeeQueue, Mocha.class, 4);
		Fill2.fill(coffeeQueue, Latte.class, 4);
		for(Coffee c : coffeeQueue){
			System.out.println(c);
		}
	}
}
