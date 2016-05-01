package org.hope6537.note.design.bulider.pattern;

/**
 * 具体建造者
 */
public class ConcreteBuilder extends Builder {

    private Product product = new Product();

    @Override
    public void setPart() {
        /*
         * 产品类的逻辑处理
		 */
    }

    @Override
    public Product buildProduct() {
        return product;
    }

}
