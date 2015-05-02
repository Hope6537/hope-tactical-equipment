package org.hope6537.note.design.adapter.example;

/**
 * 适配器，衔接被适配类和目标类
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
        System.out.println("yep~");
        super.doSomething();
    }
}
