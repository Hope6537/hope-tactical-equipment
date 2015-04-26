package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 事件处理枚举
 */
public enum EventCustomerType {

    NEW(1), DEL(2), EDIT(3), CLONE(4);

    private int value = 0;

    EventCustomerType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
