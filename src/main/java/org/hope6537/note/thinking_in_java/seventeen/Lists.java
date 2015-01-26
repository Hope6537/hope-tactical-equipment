package org.hope6537.note.thinking_in_java.seventeen;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Lists {

	private static boolean b;
	private static int i;
	private static String s;
	private static Iterator<String> it;
	private static ListIterator<String> lit;

	/**
	 * @descirbe List的各个基本操作
	 * @author Hope6537(赵鹏)
	 * @param a
	 * @signDate 2014年7月23日上午11:21:20
	 * @version 0.9
	 */
	public static void basicTest(List<String> a) {
		a.add(1, "x");
		a.add("x");
		a.addAll(Countries2.names(25));
		a.addAll(3, Countries2.names(25));
		b = a.contains("1");
		b = a.containsAll(Countries2.names(25));
		s = a.get(1);
		i = a.indexOf("1");
		b = a.isEmpty();
		it = a.iterator();
		lit = a.listIterator();
		lit = a.listIterator(3);
		i = a.lastIndexOf("1");
		a.remove(1);
		a.remove("3");
		a.set(1, "y");
		a.retainAll(Countries2.names(30));
		a.removeAll(Countries2.names(25));
		i = a.size();
		a.clear();
	}

	/**
	 * @descirbe 基本的List迭代器的操作
	 * @author Hope6537(赵鹏)
	 * @param a
	 * @signDate 2014年7月23日上午11:22:48
	 * @version 0.9
	 */
	public static void iterMotion(List<String> a) {
		ListIterator<String> it = a.listIterator();
		b = it.hasNext();
		b = it.hasPrevious();
		s = it.next();
		i = it.nextIndex();
		s = it.previous();
		i = it.previousIndex();
	}

	/**
	 * @descirbe 使用迭代器进行修改操作
	 * @author Hope6537(赵鹏)
	 * @param a
	 * @signDate 2014年7月23日上午11:24:14
	 * @version 0.9
	 */
	public static void iterManipulation(List<String> a) {
		ListIterator<String> it = a.listIterator();
		it.add("47");
		it.next();
		it.remove();
		it.set("47");
	}

}
