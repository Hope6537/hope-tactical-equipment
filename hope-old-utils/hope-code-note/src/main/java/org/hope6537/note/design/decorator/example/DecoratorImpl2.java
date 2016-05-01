package org.hope6537.note.design.decorator.example;

/**
 * 具体装饰类
 */
public class DecoratorImpl2 extends Decorator {


    /**
     * 通过构造函数传递被装饰者
     *
     * @param component 被装饰者
     */
    public DecoratorImpl2(Component component) {
        super(component);
    }

    /**
     * 自定义方法
     */
    private void customMethod() {
        System.out.println("打扫了一下屋子");
    }

    @Override
    public void operate() {
        this.customMethod();
        super.operate();
    }
}
