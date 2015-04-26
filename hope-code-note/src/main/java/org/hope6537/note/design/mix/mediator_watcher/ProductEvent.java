package org.hope6537.note.design.mix.mediator_watcher;

import java.util.Observable;

/**
 * 产品事件 继承观察者Java类
 */
public class ProductEvent extends Observable {

    private Product source;

    private ProductEventType type;


    public ProductEvent(Product source) {
        this(source, ProductEventType.NEW_PRODUCT);
    }

    public ProductEvent(Product p, ProductEventType productEventType) {
        source = p;
        type = productEventType;
        //事件触发
        notifyEventDispatch();
    }

    public Product getSource() {
        return source;
    }

    public ProductEventType getType() {
        return type;
    }

    /**
     * 提示状态已改变
     */
    private void notifyEventDispatch() {
        super.addObserver(EventDispatch.getEventDispatch());
        super.setChanged();
        super.notifyObservers(source);
    }

}

