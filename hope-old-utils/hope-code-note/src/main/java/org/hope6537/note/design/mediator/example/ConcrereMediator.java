package org.hope6537.note.design.mediator.example;

/**
 * 通用中介者 实例
 */
public class ConcrereMediator extends Mediator {


    @Override
    public void doSomethingforc1() {
        System.out.println("接受委托c1");
        getC1().selfMethod1();
        getC2().selfMethod2();
    }

    @Override
    public void doSomethingforc2() {
        System.out.println("接受委托c2");
        getC1().selfMethod1();
        getC2().selfMethod2();
    }
}
