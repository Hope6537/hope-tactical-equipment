package org.hope6537.note.design.memento.example;

/**
 * 备忘录模式
 */
public class Client {

    public static void main(String[] args) {
        /**
         * 一个状态对象
         */
        Originator originator = new Originator();
        /**
         * 一个状态容器
         */
        Caretaker caretaker = new Caretaker();
        /**
         * 设定一个备忘录
         */
        caretaker.setMemento(originator.createMemento());
        /**
         * 恢复备忘录中的状态
         */
        originator.restoreMemento(caretaker.getMemento());
    }

}
