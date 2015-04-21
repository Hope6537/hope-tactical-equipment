package org.hope6537.note.design.interpreter.math;

import java.util.HashMap;

/**
 * Created by Hope6537 on 2015/4/21.
 */
public class SubExpression extends SymbolExpression {

    public SubExpression(AbstractExpression left, AbstractExpression right) {
        super(left, right);
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return super.left.interpreter(var) - super.right.interpreter(var);
    }
}
