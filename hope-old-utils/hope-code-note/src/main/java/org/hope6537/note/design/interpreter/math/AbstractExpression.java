package org.hope6537.note.design.interpreter.math;

import java.util.HashMap;

/**
 * 抽象表达式类
 */
public abstract class AbstractExpression {

    /**
     * 解析公式和数值、其中key是参数，value是具体的数字
     */
    public abstract int interpreter(HashMap<String, Integer> var);

}
