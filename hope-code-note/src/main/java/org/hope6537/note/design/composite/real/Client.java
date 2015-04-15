package org.hope6537.note.design.composite.real;

import org.junit.Test;

public class Client {

    public static void display(Component root) {
        root.getChildren().forEach(item -> {
            if (item instanceof Leaf) {
                item.doSomething();
            } else {
                //在这里看出区别，符合良好拓展、不需要强转
                display(item);
            }
        });
    }

    @Test
    public void test() {
        Component root = new Composite();
        root.doSomething();
        Composite branch = new Composite();
        Leaf leaf = new Leaf();
        root.add(branch);
        branch.add(leaf);
        display(root);
    }

}
