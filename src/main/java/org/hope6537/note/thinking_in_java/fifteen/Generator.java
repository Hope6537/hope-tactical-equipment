package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 生成器接口
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:30:20
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public interface Generator<T> {
	T next();
}
