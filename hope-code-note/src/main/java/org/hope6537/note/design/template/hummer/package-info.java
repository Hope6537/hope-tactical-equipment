/**
 * <pre>
 *模板方法模式：
 *定义一个操作中的算法的框架，而将一些步骤延迟到子类中去，使得子类可以在不改变一个算法的结构，即可重定义该算法的某些特定步骤
 *
 *例子查看:
 *抽象模型
 *{@link org.hope6537.note.design.modelfunction.hummer.AbstractHummerModel}
 *实例子类模型
 *{@link org.hope6537.note.design.modelfunction.hummer.HummerH1Model}
 *{@link org.hope6537.note.design.modelfunction.hummer.HummerH2Model}
 *运行客户端
 *{@link org.hope6537.note.design.modelfunction.hummer.Client}
 *基本方法：一般被称为基本操作，由子类来实现
 *
 *模板方法：实现对基本方法的调度，完成固定的逻辑(一般被定义为final方法)
 *
 *下面是一个抽象的模板类
 * abstract class AbstractClass {
 protected abstract void method1();
 protected abstract void method2();
 public final void run(){
 method1();
 method2();
 }
 *}
 *
 *模板方法模式的优点：
 *1、封装不变部分，拓展可变部分
 *2、提取公共部分代码，便于维护
 *3、行为由父类控制，子类实现
 *
 *模板方法模式的缺点：
 *抽象类定义了部分抽象方法，由子类实现，子类的结果影响了父类的结果
 *也就是说子类对父类产生了影响，这不符合设计原则 
 *
 *模板方法模式的使用场景:
 *1、多个子类公有的方法，同时逻辑相同
 *2、重要的，复杂的算法，可以把核心算法设计为模板方法，细节由子类来实现（即不同的算法参数）
 *3、重构时使用，把相同的代码抽取到父类中，然后通过钩子函数来约定其行为
 *
 *模板方法模式的拓展:
 *增加一个在run方法执行的时候的判定模式、即针对不同的实现子类，有着在逻辑上近似但不同的算法执行步骤
 *抽象模板的类
 *{@link org.hope6537.note.design.modelfunction.hummer.HummerModel_G}
 *主要在run方法的业务流程上进行了修改
 *并增加了isAlarm方法,用来进行是否执行某个步骤的判定
 *对于子类
 *{@link org.hope6537.note.design.modelfunction.hummer.HummerH1Model_G}
 *没有进行修改
 *而对于子类
 *{@link org.hope6537.note.design.modelfunction.hummer.HummerH2Model_G}
 *通过覆写isAlarm方法，来更改父类的控制流程
 *这就是钩子方法
 *
 *<hr>
 *总结：
 *关于父类调用子类的方法的问题，永远不要做以下的步骤
 *1、把子类传递到父类的有参构造中，然后调用
 *2、使用反射的方式调用
 *3、调用子类的静态方法
 *这些都是不被允许的（在项目中）
 *如果我们一定要使用子类中的特定方法，我们为啥要让这个子类继承父类呢？
 *模板方法设计模式的核心就是：
 *父类建立框架，子类在重写了父类的部分方法之后，在调用从父类中继承的方法，产生不同的<b>结果</b>
 *</pre>
 */
package org.hope6537.note.design.template.hummer;

