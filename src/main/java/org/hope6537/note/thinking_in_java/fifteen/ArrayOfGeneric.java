package org.hope6537.note.thinking_in_java.fifteen;

class Generic<T> {
}

class ArrayOfGenericRefenerce {
    static Generic<Integer>[] gia;
}

/**
 * @version 0.9
 * @Describe 数组泛型 使用持有对象的方式
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午03:31:19
 * @company Changchun University&SHXT
 */
public class ArrayOfGeneric {
    static final int SIZE = 100;
    static Generic<Integer>[] gia;

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        // 这样才编译有效？
        gia = (Generic<Integer>[]) new Generic[SIZE];
        // 必须这样插入对象
        gia[0] = new Generic<Integer>();
    }
}
