package org.hope6537.note.design.memento.backups;

import org.hope6537.note.design.memento.example.Memento;

import java.util.HashMap;
import java.util.Map;

/**
 * 多个备忘录模式的备忘录容器
 */
public class CareTaker {
    private Map<String, Memento> mementoMap = new HashMap<>();

    public Memento getMemento(String index) {
        return mementoMap.get(index);
    }

    public void setMemento(String index, Memento memento) {
        this.mementoMap.put(index, memento);
    }
}
