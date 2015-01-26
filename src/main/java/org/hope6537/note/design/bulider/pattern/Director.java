package org.hope6537.note.design.bulider.pattern;

public class Director {

	private Builder builder = new ConcreteProduct();

	public Product getProduct() {
		builder.setPart();
		/*
		 * 设置不同的零件 产生不同的顺序
		 */
		return builder.buildProduct();
	}

}
