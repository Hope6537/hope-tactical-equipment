package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 事件类型定义
 */
public enum ProductEventType {

    NEW_PRODUCT(1), DEL_PRODUCT(2), EDIT_PRODUCT(3), CLONE_PRODUCT(4);

    private int value = 0;

    ProductEventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
