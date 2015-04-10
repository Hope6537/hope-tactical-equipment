package org.hope6537.note.design.watcher.example;

import java.util.Vector;

/**
 * 被观察者
 */
public abstract class Subject {

    private Vector<Observer> vector = new Vector<>();

    /**
     * 增加和删除观察者
     */
    public void addObserver(Observer observer) {
        this.vector.add(observer);
    }

    public void delObserver(Observer observer) {
        this.vector.remove(observer);
    }

    /**
     * 通知所有观察者
     */
    public void notifyObservers() {
        this.vector.forEach(Observer::update);
    }
}
