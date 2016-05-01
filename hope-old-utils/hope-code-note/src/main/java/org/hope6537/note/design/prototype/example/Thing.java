package org.hope6537.note.design.prototype.example;

/**
 * 原型模式实例
 */
public class Thing implements Cloneable {

    public Thing() {
        System.out.println("对象被创建");
    }

    @Override
    protected Thing clone() throws CloneNotSupportedException {
        Thing thing = null;
        try {
            thing = (Thing) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return thing;
    }
}
