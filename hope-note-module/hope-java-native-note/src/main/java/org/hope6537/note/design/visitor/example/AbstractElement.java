package org.hope6537.note.design.visitor.example;

/**
 * 抽象元素
 */
public abstract class AbstractElement {

    public abstract void doSomething();

    public abstract void accept(Visitor visitor);

}
