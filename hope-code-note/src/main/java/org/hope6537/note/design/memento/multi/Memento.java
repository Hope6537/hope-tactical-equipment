package org.hope6537.note.design.memento.multi;

import java.util.Map;

/**
 * Created by Hope6537 on 2015/4/19.
 */
public class Memento {

    private Map<String, Object> stateMap;

    public Map<String, Object> getStateMap() {
        return stateMap;
    }

    public void setStateMap(Map<String, Object> stateMap) {
        this.stateMap = stateMap;
    }

    public Memento(Map<String, Object> stringObjectMap) {
        this.stateMap = stringObjectMap;
    }
}
