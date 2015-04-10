package org.hope6537.note.design.composite.real;

import java.util.List;

/**
 * 透明组合模式、好处是不会进行强制的类型转化，符合依赖倒转原则
 * 总体抽象构件
 */
public abstract class Component {

    public void doSomething() {
        System.out.println("业务逻辑");
    }

    public abstract void add(Component component);

    public abstract void remove(Component component);

    public abstract List<Component> getChildren();

}
