package org.hope6537.note.design.prototype.example;

import org.junit.Test;

public class Client {

    /**
     * 输出:
     * >对象被创建
     * 仅仅被创建一次
     */
    @Test
    public void test() throws CloneNotSupportedException {
        Thing thing = new Thing();
        Thing clone = thing.clone();
    }

}
