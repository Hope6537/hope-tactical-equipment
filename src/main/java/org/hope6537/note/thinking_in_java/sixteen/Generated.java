package org.hope6537.note.thinking_in_java.sixteen;

import org.hope6537.note.thinking_in_java.fifteen.Generator;

/**
 * @describe 生成基本类型数组
 * @author Hope6537(赵鹏)
 * @signdate 2014-7-22下午02:57:03
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Generated {

	/**
	 * @descirbe 通过已有数组然后生成生成器数据填充，然后返回
	 * @author Hope6537(赵鹏)
	 * @param <T>
	 * @param a
	 * @param gen
	 * @return
	 * @signDate 2014-7-22下午02:59:52
	 * @version 0.9
	 */
	public static <T> T[] array(T[] a, Generator<T> gen) {
		return (new CollectionData<T>(gen, a.length)).toArray(a);
	}

	/**
	 * @descirbe 给定类型和大小，现生成数组然后填充
	 * @author Hope6537(赵鹏)
	 * @param <T>
	 * @param type
	 * @param gen
	 * @param size
	 * @return
	 * @signDate 2014-7-22下午03:03:19
	 * @version 0.9
	 */
	public static <T> T[] array(Class<T> type, Generator<T> gen, int size) {
		T[] a = (T[]) java.lang.reflect.Array.newInstance(type, size);
		return (new CollectionData<T>(gen, size)).toArray(a);
	}

}
