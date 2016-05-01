package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 事件处理者3-乞丐
 */
public class Beggar extends EventCustomer {
    public Beggar() {
        super(EventCustomerType.DEL);
    }

    @Override
    public void exec(ProductEvent event) {
        Product product = event.getSource();
        ProductEventType type = event.getType();
        System.out.println("乞丐处理事件 " + product.getName() + "销毁、事件类型 " + type);
    }
}
