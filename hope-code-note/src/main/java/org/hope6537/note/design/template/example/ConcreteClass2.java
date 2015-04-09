package org.hope6537.note.design.template.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class ConcreteClass2 extends AbstractClass {

    private final boolean b;

    public ConcreteClass2(boolean b) {
        super();
        this.b = b;
    }

    @Override
    protected void doSomething() {
        System.out.println("doSomething2");
    }

    @Override
    protected void doAnything() {
        System.out.println("doAnything2");
    }

    /**
     * 对方法进行处理
     */
    protected boolean someAdvice() {
        return b;
    }
}
