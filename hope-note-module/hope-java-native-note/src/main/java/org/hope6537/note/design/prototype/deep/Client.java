package org.hope6537.note.design.prototype.deep;

import org.junit.Test;

public class Client {

    /**
     * 输出
     * >[test]
     * 深拷贝、就和从前一刀两段了
     */
    @Test
    public void test() throws CloneNotSupportedException {

        Thing thing = new Thing();
        thing.setValue("test");

        Thing clone = thing.clone();
        clone.setValue("test233");

        System.out.println(thing.getValue().toString());
    }
}
