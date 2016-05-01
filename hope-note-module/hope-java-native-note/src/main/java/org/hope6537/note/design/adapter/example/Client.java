package org.hope6537.note.design.adapter.example;

import org.junit.Test;

public class Client {

    @Test
    public void test() {
        Target target = new TagretImpl();
        target.request();

        Target target2 = new Adapter();
        target2.request();
    }
}
