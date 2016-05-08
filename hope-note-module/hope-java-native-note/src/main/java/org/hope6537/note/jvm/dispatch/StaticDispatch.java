package org.hope6537.note.jvm.dispatch;

/**
 * 静态分派示例代码
 * Created by hope6537 on 16/5/8.
 */
public class StaticDispatch {

    static abstract class Human {
    }

    static class Man extends Human {

    }

    static class Woman extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("guy");
    }

    public void sayHello(Man guy) {
        System.out.println("man");
    }

    public void sayHello(Woman guy) {
        System.out.println("lady");
    }

    /**
     * 输出
     * guy
     * guy
     * @param args
     */
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayHello(man);
        staticDispatch.sayHello(woman);
    }
}

