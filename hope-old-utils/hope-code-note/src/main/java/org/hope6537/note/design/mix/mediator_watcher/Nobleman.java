package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 事件处理者2-贵族
 */
public class Nobleman extends EventCustomer {

    /**
     * 定义它的事件处理级别
     */
    public Nobleman() {
        super(EventCustomerType.EDIT);
        super.addCustomType(EventCustomerType.CLONE);
    }

    @Override
    public void exec(ProductEvent event) {
        Product product = event.getSource();
        ProductEventType type = event.getType();
        if (type.getValue() == EventCustomerType.CLONE.getValue()) {
            System.out.println("贵族处理事件 " + product.getName() + "克隆、事件类型 " + type);
        } else {
            System.out.println("贵族处理事件 " + product.getName() + "修改、事件类型 " + type);
        }
    }
}
