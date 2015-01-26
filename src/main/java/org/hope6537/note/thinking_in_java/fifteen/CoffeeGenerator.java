package org.hope6537.note.thinking_in_java.fifteen;

import java.util.Iterator;
import java.util.Random;

/**
 * @Describe 生成器
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:42:02
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {
	@SuppressWarnings("unchecked")
	private Class[] types = { Latte.class, Mocha.class, Cappuccino.class,
			Americano.class, Breve.class };
	private static Random rand = new Random(47);

	public CoffeeGenerator() {
	}

	private int size = 0;

	public CoffeeGenerator(int sz) {
		size = sz;
	}

	@Override
	public Coffee next() {
		try {
			return (Coffee) types[rand.nextInt(types.length)].newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeIterator();
	}

	class CoffeeIterator implements Iterator<Coffee> {
		int count = size;

		public boolean hasNext() {
			return count > 0;
		}

		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();
		}

		@Override
		public void remove() {
			System.out.println("No Remove");
		}
	}

	public static void main(String[] args) {
		CoffeeGenerator generator = new CoffeeGenerator();
		for (int i = 0; i < 5; i++) {
			System.out.println(generator.next());
		}
		for(Coffee c :new CoffeeGenerator(5)){
			System.out.println(c);
		}
	}
}
