package org.hope6537.note.tij.sixteen;

import org.hope6537.note.tij.fifteen.Generator;

import java.util.ArrayList;

/**
 * @param <T>
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 生成一个通过基本类型生成器填充好的Collection集合 繼承自ArrayList
 * @signdate 2014-7-22下午03:03:43
 * @company Changchun University&SHXT
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
     * @param <T>
     * @param gen
     * @param quantity
     * @return
     * @descirbe 返回一个CollectionData对象 填充好了的
     * @author Hope6537(赵鹏)
     * @signDate 2014-7-22下午03:04:16
     * @version 0.9
     */
    public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
        return new CollectionData<T>(gen, quantity);
    }
} 
