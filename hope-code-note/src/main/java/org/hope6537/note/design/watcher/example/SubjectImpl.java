package org.hope6537.note.design.watcher.example;

/**
 * Created by Hope6537 on 2015/4/10.
 */
public class SubjectImpl extends Subject {

    public void doSomething() {
        System.out.println("进行了些业务逻辑");
        super.notifyObservers();
    }
}
