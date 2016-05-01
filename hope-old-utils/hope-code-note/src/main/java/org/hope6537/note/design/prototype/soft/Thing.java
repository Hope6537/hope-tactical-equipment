package org.hope6537.note.design.prototype.soft;

import java.util.ArrayList;

/**
 * 浅拷贝对象
 */
public class Thing implements Cloneable {

    private ArrayList<String> arrayList = new ArrayList<>();

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

    public ArrayList<String> getValue() {
        return this.arrayList;
    }

    public void setValue(String value) {
        this.arrayList.add(value);
    }
}
