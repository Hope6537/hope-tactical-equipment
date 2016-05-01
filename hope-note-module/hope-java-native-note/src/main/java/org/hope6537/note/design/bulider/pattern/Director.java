package org.hope6537.note.design.bulider.pattern;

/**
 * 导演类
 * 通过它的设置来组装对象
 */
public class Director {

    private Builder builder = new ConcreteBuilder();

    public Product getProduct() {
        builder.setPart();
        /*
         * 设置不同的零件 产生不同的顺序
		 */
        return builder.buildProduct();
    }

}
