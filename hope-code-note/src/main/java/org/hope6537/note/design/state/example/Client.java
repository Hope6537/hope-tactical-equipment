package org.hope6537.note.design.state.example;

/**
 * 状态模式场景类
 */
public class Client {

    public static void main(String[] args) {
        Context context = new Context();
        context.setCurrentState(new State1());
        context.handle1();
        context.handle2();
    }
}
