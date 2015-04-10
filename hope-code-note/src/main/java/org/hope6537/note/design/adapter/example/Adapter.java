package org.hope6537.note.design.adapter.example;

/**
 * Created by Hope6537 on 2015/4/10.
 */
public class Adapter extends Adaptee implements Target {

    @Override
    public void request() {
        System.out.println("yep~");
        super.doSomething();
    }
}
