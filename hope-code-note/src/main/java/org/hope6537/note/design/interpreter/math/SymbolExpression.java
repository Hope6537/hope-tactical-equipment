package org.hope6537.note.design.interpreter.math;

/**
 * 抽象运算符解析器
 */
public abstract class SymbolExpression extends AbstractExpression {

    protected AbstractExpression left;
    protected AbstractExpression right;

    public SymbolExpression(AbstractExpression left, AbstractExpression right) {
        this.left = left;
        this.right = right;
    }
}
