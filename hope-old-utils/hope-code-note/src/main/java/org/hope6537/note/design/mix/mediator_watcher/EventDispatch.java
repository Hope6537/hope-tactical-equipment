package org.hope6537.note.design.mix.mediator_watcher;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * 事件的观察者
 */
public class EventDispatch implements Observer {

    private final static EventDispatch EVENT_DISPATCH = new EventDispatch();

    private Vector<EventCustomer> customers = new Vector<>();

    private EventDispatch() {

    }

    public static EventDispatch getEventDispatch() {
        return EVENT_DISPATCH;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("change");
        Product product = (Product) arg;
        ProductEvent event = (ProductEvent) o;
        //处理者处理，中介者模式的核心
        for (EventCustomer customer : customers) {
            for (EventCustomerType t : customer.getCustomerTypes()) {
                if (t.getValue() == event.getType().getValue()) {
                    customer.exec(event);
                }
            }
        }
    }

    /**
     * 注册事件
     */
    public void registerCustomer(EventCustomer customer) {
        customers.add(customer);
    }
}
