package org.hope6537.note.design.interpreter.example;

import java.util.Stack;

/**
 * 解释器模式场景类
 */
public class Client {

    public static void main(String[] args) {
        Context context = new Context();
        Stack<AbstractExpression> stack = null;
        for (; ; ) {
            //进行语法判断并产生递归调用
            break;
        }
        assert stack != null;
        AbstractExpression exp = stack.pop();
        exp.interpreter(context);
    }

}
