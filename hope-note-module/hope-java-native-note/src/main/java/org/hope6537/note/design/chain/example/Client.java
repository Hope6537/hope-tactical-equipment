package org.hope6537.note.design.chain.example;

import org.junit.Test;

/**
 * 责任链模式场景类
 */
public class Client {

    @Test
    public void test() {
        Handler handler1 = new HandlerImpl1();
        Handler handler2 = new HandlerImpl2();
        Handler handler3 = new HandlerImpl3();

        handler1.setNextHandler(handler2);
        handler2.setNextHandler(handler3);

        Response response = handler1.handleMessage(new Request(Level.ERROR));
        System.out.println(response.getMessage());

    }

}
