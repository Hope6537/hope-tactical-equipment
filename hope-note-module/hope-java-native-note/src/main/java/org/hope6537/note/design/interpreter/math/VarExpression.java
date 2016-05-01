package org.hope6537.note.design.interpreter.math;

import java.util.HashMap;

/**
 * 变量解析器
 */
public class VarExpression extends AbstractExpression {

    private String key;

    public VarExpression(String key) {
        this.key = key;

    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(this.key);
    }

}
