package org.hope6537.effectivejava.chapter4;

import org.hope6537.java8.Person;
import org.junit.Test;

/**
 * Created by threegrand1 on 15-1-8.
 */
public class ClassTest {

    /**
     * 首先要解决的是一个小问题，如果一个对象会暴露出一个公有的数组
     * 那么这个数组应当是final的 但是问题是如果final与包含可变对象的引用，引用自身将无法修改
     * 但是引用的对象却可以被修改，也就是说数组的内部数据将会玩完
     * 例如下面这个数据
     */

    //长度非零的数组总是可变的，所以这种方法是错误的。
    //如果类具有这样的变量，那么客户端就能够修改数组内部的内容，这是大部分安全漏洞的根源
    public static final Person[] VALUES = {};
    @Test
    public void testFieldArray() {

    }
}
