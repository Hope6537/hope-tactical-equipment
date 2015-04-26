package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 事件处理者1-平民
 */
public class Commoner extends EventCustomer {

    public Commoner() {
        super(EventCustomerType.NEW);
    }

    @Override
    public void exec(ProductEvent event) {
        //事件的源头
        Product product = event.getSource();
        ProductEventType type = event.getType();
        System.out.println("平民处理事件" + product.getName() + "诞生记，事件类型 " + type);
    }
}
