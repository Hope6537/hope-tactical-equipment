package org.hope6537.note.design.visitor.example;

/**
 * 访问者抽象接口
 */
public interface Visitor {

    /**
     * 可以访问哪些对象
     */
    public void visit(AbstractElement element);
}
