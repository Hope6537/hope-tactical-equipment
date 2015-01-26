package org.hope6537.note.design.factory.example;

/** 
 * <p>
 * 工厂模式的优点：<br/>
 * 1、良好的封装性，代码结构清晰，创建一个对象的过程并不需要了解具体构造器的实现，只需要调用工厂方法即可，降低耦合性<br/>
 * 2、拓展性优秀，如果存在另一个该结构树的新对象，工厂类不需要修改即可投入使用<br/>
 * 3、屏蔽产品类，直接面向接口编程，调用者不必担心产品的变化<br/>
 * 4、优秀的解耦，高层模块只需要知道产品的抽象类，其他的实现都不需要关心<br/>
 * <hr>
 * 工厂模式的使用场景：<br/>
 * 我们需要慎重考虑增加代码复杂度建立工厂是否明智<br/>
 * 1、需要灵活，可拓展的框架时<br/>
 * 2、使用异构项目时<br/>
 * 3、在进行测试驱动开发的框架下<br/>
 * </p>
 * <p>DevelopedTime: 2014年9月5日下午4:29:02</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class Client {

	public static void main(String[] args) {
		AbstractFactory factory = new ConcreteCreator();
		Product product = factory.createProduct(CurrentProduct1.class);
	}
}
