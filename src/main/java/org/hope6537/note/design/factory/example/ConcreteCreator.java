package org.hope6537.note.design.factory.example;

/** 
 * <p>Describe: 具体实现的工厂</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午4:00:13</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class ConcreteCreator extends AbstractFactory {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.hope6537.note.design.factory.example.AbstractFactory#createProduct(java
	 * .lang.Class)
	 * @Change:Hope6537
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Product> T createProduct(Class<T> cls) {
		Product product = null;

		try {
			// 进行类型反射实例化
			product = (T) Class.forName(cls.getName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (T) product;
	}

}
