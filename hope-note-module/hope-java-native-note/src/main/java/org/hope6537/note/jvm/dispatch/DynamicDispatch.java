package org.hope6537.note.jvm.dispatch;

/**
 * 动态分派示例代码
 * Created by hope6537 on 16/5/8.
 */
public class DynamicDispatch {

    static abstract class Human {
        protected abstract void sayHello();
    }

    static class Man extends Human {

        @Override
        protected void sayHello() {
            System.out.println("man");
        }
    }

    static class Woman extends Human {

        @Override
        protected void sayHello() {
            System.out.println("woman");
        }
    }

    /**
     * 输出
     * man
     * woman
     * woman
     * @param args
     */
    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        man.sayHello();
        woman.sayHello();
        man = new Woman();
        man.sayHello();
    }
}
