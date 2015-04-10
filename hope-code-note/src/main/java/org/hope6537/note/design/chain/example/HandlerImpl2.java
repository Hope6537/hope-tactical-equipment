package org.hope6537.note.design.chain.example;

/**
 * Created by Hope6537 on 2015/4/10.
 */
public class HandlerImpl2 extends Handler {
    @Override
    protected Level getHandlerLevel() {
        return new Level(Level.ERROR);
    }

    @Override
    protected Response echo(Request request) {
        return new Response("handler2");
    }
}
