package org.hope6537.note.design.visitor.example;

/**
 * 具体访问者
 */
public class VisitorImpl implements Visitor {

    @Override
    public void visit(AbstractElement element) {
        element.doSomething();
    }

}
