package org.hope6537.note.design.chain.example;

/**
 * 具体责任人
 */
public class HandlerImpl3 extends Handler {
    @Override
    protected Level getHandlerLevel() {
        return new Level(Level.ERROR);
    }

    @Override
    protected Response echo(Request request) {
        return new Response("handler3");
    }
}
