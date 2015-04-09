package org.hope6537.note.design.prototype.soft;

import org.junit.Test;

public class Client {

    /**
     * 输出
     * >[test, test2]
     * 浅拷贝的话
     * 数组对象还是指向原生对象的引用的
     * 所以会修改到源对象
     */
    @Test
    public void test() throws CloneNotSupportedException {

        Thing thing = new Thing();
        thing.setValue("test");

        Thing clone = thing.clone();
        clone.setValue("test2");

        System.out.println(thing.getValue().toString());
    }
}
