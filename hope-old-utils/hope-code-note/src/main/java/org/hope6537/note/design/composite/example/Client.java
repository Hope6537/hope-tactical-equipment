package org.hope6537.note.design.composite.example;

import org.junit.Test;

/**
 * 组件模式场景类
 */
public class Client {

    public static void display(Composite root) {
        /**
         * 需要强制类型转换来进行显示
         */
        root.getChildren().forEach(item -> {
            if (item instanceof Leaf) {
                item.doSomething();
            } else {
                display((Composite) item);
            }
        });
    }

    @Test
    public void test() {
        Composite root = new Composite();
        root.doSomething();

        Composite branch = new Composite();

        Leaf leaf = new Leaf();
        root.add(branch);
        branch.add(leaf);

        display(root);
    }

}
