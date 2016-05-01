package org.hope6537.note.design.mediator.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class ConcreteColleague2 extends Colleague {

    public ConcreteColleague2(Mediator mediator) {
        super(mediator);
    }

    /**
     * 以及自身的业务逻辑
     */
    public void selfMethod1() {
        System.out.println("我能自己处理");
    }

    public void selfMethod2() {
        //自身处理不了的逻辑，交给中介者处理
        System.out.println("我不能自己处理，委托给中介者c2");
        super.mediator.doSomethingforc2();

    }
}
