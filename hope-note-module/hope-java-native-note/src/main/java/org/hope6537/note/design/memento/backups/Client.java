package org.hope6537.note.design.memento.backups;

import org.hope6537.note.design.memento.example.Originator;

/**
 * 多重备份模式下的场景类
 */
public class Client {

    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("状态1");

        CareTaker careTaker = new CareTaker();

        careTaker.setMemento("001", originator.createMemento());

        originator.setState("状态2");
        careTaker.setMemento("002", originator.createMemento());

        System.out.println("当前状态 " + originator.getState());

        originator.restoreMemento(careTaker.getMemento("001"));

        System.out.println("还原1后 " + originator.getState());

        originator.restoreMemento(careTaker.getMemento("002"));

        System.out.println("还原2后 " + originator.getState());

    }
}
