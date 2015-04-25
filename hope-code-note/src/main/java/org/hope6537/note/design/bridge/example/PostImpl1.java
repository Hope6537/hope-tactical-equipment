package org.hope6537.note.design.bridge.example;

/**
 * 实现化角色1
 */
public class PostImpl1 implements Post {


    @Override
    public void method1() {
        System.out.println("1->1");
    }

    @Override
    public void method2() {
        System.out.println("2->1");
    }
}
