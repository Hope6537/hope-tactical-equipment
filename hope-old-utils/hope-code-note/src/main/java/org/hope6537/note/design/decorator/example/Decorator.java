package org.hope6537.note.design.decorator.example;

/**
 * 抽象装饰者
 */
public abstract class Decorator extends Component {

    private Component component = null;

    /**
     * 通过构造函数传递被装饰者
     */
    public Decorator(Component component) {
        this.component = component;
    }

    /**
     * 委托被装饰者执行
     */
    @Override
    public void operate() {
        this.component.operate();
    }
}
