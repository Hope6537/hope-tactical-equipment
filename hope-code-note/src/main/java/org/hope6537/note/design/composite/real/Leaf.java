package org.hope6537.note.design.composite.real;

import java.util.List;

/**
 * 透明组合模式的节点
 */
public class Leaf extends Component {

    @Override
    public void doSomething() {
        System.out.println("叶子");
    }

    @Deprecated
    @Override
    public void add(Component component) {
        //空实现
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public List<Component> getChildren() {
        throw new UnsupportedOperationException();
    }
}
