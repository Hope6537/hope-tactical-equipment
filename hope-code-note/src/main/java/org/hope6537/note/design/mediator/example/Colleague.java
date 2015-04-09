package org.hope6537.note.design.mediator.example;

/**
 * 抽象独立类
 */
public abstract class Colleague {

    /**
     * 持有和中介者通信的对象
     */
    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }
}
