package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 场景类
 */
public class Client {

    public static void main(String[] args) {

        EventDispatch dispatch = EventDispatch.getEventDispatch();
        dispatch.registerCustomer(new Beggar());
        dispatch.registerCustomer(new Commoner());
        dispatch.registerCustomer(new Nobleman());
        ProductManager manager = new ProductManager();
        System.out.println("==模拟创建产品==");
        Product product = manager.createProduct("小男孩");
        System.out.println("==模拟修改产品==");
        manager.editProduct(product, "胖子号");
        System.out.println("==模拟克隆产品==");
        manager.clone(product);
        System.out.println("==模拟销毁产品==");
        manager.abandonProduct(product);

    }

}
