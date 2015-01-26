package org.hope6537.note.thinking_in_java.sixteen;

import org.hope6537.note.thinking_in_java.fifteen.Generator;

import java.util.ArrayList;

/**
 * @describe 生成一个通过基本类型生成器填充好的Collection集合 繼承自ArrayList
 * @author Hope6537(赵鹏)
 * @signdate 2014-7-22下午03:03:43
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class CollectionData<T> extends ArrayList<T> {
	/**
	 * @describe
	 */
	private static final long serialVersionUID = 7831981403355543936L;

	public CollectionData(Generator<T> gen, int quantity) {
		for (int i = 0; i < quantity; i++)
			add(gen.next());
	}

	/**
	 * @descirbe 返回一个CollectionData对象 填充好了的
	 * @author Hope6537(赵鹏)
	 * @param <T>
	 * @param gen
	 * @param quantity
	 * @return
	 * @signDate 2014-7-22下午03:04:16
	 * @version 0.9
	 */
	public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
		return new CollectionData<T>(gen, quantity);
	}
} 
