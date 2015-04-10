package org.hope6537.note.design.composite.example;

/**
 * 叶子构件
 */
public class Leaf extends Component {

    @Override
    public void doSomething() {
        System.out.println("叶子");
    }
}
