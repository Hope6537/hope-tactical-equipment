package org.hope6537.note.design.memento.example;

/**
 * 备忘录模式下的发起人角色
 */
public class Originator {

    private String state = "";

    public Memento createMemento() {
        return new Memento(this.getState());
    }

    public void restoreMemento(Memento _memento) {
        this.setState(_memento.getState());
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
