package org.hope6537.note.design.decorator.example;

/**
 * 具体装饰类
 */
public class DecoratorImpl1 extends Decorator {


    /**
     * 通过构造函数传递被装饰者
     *
     * @param component 被装饰者
     */
    public DecoratorImpl1(Component component) {
        super(component);
    }

    /**
     * 自定义方法
     */
    private void customMethod() {
        System.out.println("在门上戳了三个洞");
    }

    @Override
    public void operate() {
        this.customMethod();
        super.operate();
    }
}
