/**
 *<pre>
 * 建造者模式
 * 定义:将一个复杂对象的构建与他的表示相分离，使得同样的构建过程可以创建不同的表示
 * 在建造者模式中有如下四个角色
 * {@link org.hope6537.note.design.bulider.pattern.Product} -- 产品类
 * 一般实现了模板方法模式
 * {@link org.hope6537.note.design.bulider.pattern.Builder} -- 抽象建造者
 * 规范产品的组建
 * {@link org.hope6537.note.design.bulider.pattern.ConcreteProduct} --具体建造者
 * 实现抽象类的具体建造方法
 * {@link org.hope6537.note.design.bulider.pattern.Director} -- 导演类
 *
 *</pre>
 */
package org.hope6537.note.design.bulider.pattern;