package org.hope6537.note.design.decorator.example;

import org.junit.Test;

/**
 * 具体执行类
 */
public class Client {

    @Test
    public void test() {

        Component component = new ComponentImpl();

        //第一次修饰
        component = new DecoratorImpl1(component);
        //第二次修饰
        component = new DecoratorImpl2(component);

        component.operate();

    }

}
