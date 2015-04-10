package org.hope6537.note.design.watcher.example;

/**
 * 不同的观察者实例
 */
public class ObserverImpl implements Observer {

    @Override
    public void update() {
        System.out.println("接收");
    }
}
