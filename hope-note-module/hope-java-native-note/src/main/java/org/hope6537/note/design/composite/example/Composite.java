package org.hope6537.note.design.composite.example;

import java.util.ArrayList;

/**
 * 树枝构件
 */
public class Composite extends Component {

    /**
     * 构架容器
     */
    private ArrayList<Component> componentArrayList = new ArrayList<>();

    /**
     * 子节点操作
     */
    public void add(Component component) {
        this.componentArrayList.add(component);
    }

    public void remove(Component component) {
        this.componentArrayList.remove(component);
    }

    public ArrayList<Component> getChildren() {
        return this.componentArrayList;
    }

}
