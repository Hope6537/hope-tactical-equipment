package org.hope6537.note.design.mix.mediator_watcher;

/**
 * 产品管理类
 */
public class ProductManager {

    private boolean isPermittedCreate = false;

    public Product createProduct(String name) {
        isPermittedCreate = true;
        Product product = new Product(name, this);
        new ProductEvent(product, ProductEventType.NEW_PRODUCT);
        return product;
    }

    public void abandonProduct(Product product) {
        new ProductEvent(product, ProductEventType.DEL_PRODUCT);
        product = null;

    }

    public void editProduct(Product product, String name) {
        product.setName(name);
        new ProductEvent(product, ProductEventType.EDIT_PRODUCT);
    }

    public boolean isCreateProduct() {
        return isPermittedCreate;
    }

    public Product clone(Product p) {
        new ProductEvent(p, ProductEventType.CLONE_PRODUCT);
        return p.clone();
    }
}
