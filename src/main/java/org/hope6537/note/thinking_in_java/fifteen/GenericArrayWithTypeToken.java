package org.hope6537.note.thinking_in_java.fifteen;

import java.lang.reflect.Array;

/**
 * @param <T>
 * @version 0.9
 * @Describe 数组泛型实验3 终于成功工作了
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:49:41
 * @company Changchun University&SHXT
 */
public class GenericArrayWithTypeToken<T> {

    private T[] array;

    /**
     * @param @param type
     * @param @param sz
     * @Describe 我们将类型信息放入对象中，以便数组被擦除后恢复类型
     * @Author Hope6537(赵鹏)
     */
    public GenericArrayWithTypeToken(Class<T> type, int sz) {
        array = (T[]) Array.newInstance(type, sz);
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> arrayWithTypeToken = new GenericArrayWithTypeToken<Integer>(
                Integer.class, 10);
        Integer[] ia = arrayWithTypeToken.rep();
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

}
