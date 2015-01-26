package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 基础生成器
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午02:05:34
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class BasicGenerator<T> implements Generator<T> {

	private Class<T> type;

	private BasicGenerator(Class<T> type) {
		super();
		this.type = type;
	}

	@Override
	public T next() {
		try {
			return type.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T> Generator<T> create(Class<T> type){
		return new BasicGenerator<T>(type);
	}
}
