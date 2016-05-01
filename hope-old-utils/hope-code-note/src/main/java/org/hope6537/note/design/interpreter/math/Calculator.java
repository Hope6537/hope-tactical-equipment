package org.hope6537.note.design.interpreter.math;

import java.util.HashMap;
import java.util.Stack;

/**
 * 表达式解析器封装类
 */
public class Calculator {

    /**
     * 定义表达式
     */
    private AbstractExpression expression;

    public Calculator(String expr) {

        //安排运算的先后顺序
        Stack<AbstractExpression> stack = new Stack<>();
        //表达式拆分为字符数组
        char[] charArray = expr.toCharArray();
        AbstractExpression left = null;
        AbstractExpression right = null;
        for (int i = 0; i < charArray.length; i++) {
            switch (charArray[i]) {
                case '+':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new AddExpression(left, right));
                    break;
                case '-':
                    left = stack.pop();
                    right = new VarExpression(String.valueOf(charArray[++i]));
                    stack.push(new SubExpression(left, right));
                    break;
                default:
                    stack.push(new VarExpression(String.valueOf(charArray[i])));
            }
        }
        this.expression = stack.pop();
    }

    public int run(HashMap<String, Integer> var) {
        return this.expression.interpreter(var);
    }
}

