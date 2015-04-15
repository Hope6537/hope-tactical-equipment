package org.hope6537.note.design.composite.example;

import org.junit.Test;

public class Client {

    public static void display(Composite root) {
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
