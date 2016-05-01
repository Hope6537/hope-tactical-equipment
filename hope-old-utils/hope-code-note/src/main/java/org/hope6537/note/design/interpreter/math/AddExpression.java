package org.hope6537.note.design.interpreter.math;

import java.util.HashMap;

/**
 * 具体实现运算符号解析器——加法
 */
public class AddExpression extends SymbolExpression {


    public AddExpression(AbstractExpression left, AbstractExpression right) {
        super(left, right);
    }

    /**
     * 把左右两个表达式的运算的结果加起来
     */
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
