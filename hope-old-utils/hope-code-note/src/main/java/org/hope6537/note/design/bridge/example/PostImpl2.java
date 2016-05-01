package org.hope6537.note.design.bridge.example;

/**
 * 实现化角色1
 */
public class PostImpl2 implements Post {


    @Override
    public void method1() {
        System.out.println("1->2");
    }

    @Override
    public void method2() {
        System.out.println("2->2");
    }
}
