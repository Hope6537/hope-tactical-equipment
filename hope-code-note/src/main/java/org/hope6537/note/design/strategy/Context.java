package org.hope6537.note.design.strategy;

/**
 * 封装角色
 */
public class Context {

    private Strategy strategy = null;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void doAnyThing() {
        strategy.doSomething();
    }
}
