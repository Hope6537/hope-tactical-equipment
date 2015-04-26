package org.hope6537.note.design.mix.mediator_watcher;

import java.util.Vector;

/**
 * 抽象的事件处理者
 */
public abstract class EventCustomer {

    private Vector<EventCustomerType> customerTypes = new Vector<>();

    public EventCustomer(EventCustomerType type) {
        addCustomType(type);
    }

    public void addCustomType(EventCustomerType type) {
        customerTypes.add(type);
    }

    public Vector<EventCustomerType> getCustomerTypes() {
        return customerTypes;
    }

    public abstract void exec(ProductEvent event);
}
