package org.hope6537.note.design.interpreter.example;

/**
 * 非终结符表达式 例如+ -
 */
public class NonterminalExpression extends AbstractExpression {
    /**
     * 每个非终结符表达式都会对其他的表达式产生依赖
     */
    public NonterminalExpression(AbstractExpression... expressions) {

    }

    @Override
    public Object interpreter(Context context) {
        return null;
    }
}
