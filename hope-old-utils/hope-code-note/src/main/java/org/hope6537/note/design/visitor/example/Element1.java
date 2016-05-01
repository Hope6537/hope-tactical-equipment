package org.hope6537.note.design.visitor.example;

/**
 * Created by Hope6537 on 2015/4/20.
 */
public class Element1 extends AbstractElement {
    @Override
    public void doSomething() {
        System.out.println("doSomething1");
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
