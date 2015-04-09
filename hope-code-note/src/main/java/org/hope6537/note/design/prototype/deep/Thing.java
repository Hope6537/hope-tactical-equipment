package org.hope6537.note.design.prototype.deep;

import java.util.ArrayList;

/**
 * 深拷贝对象
 */
public class Thing implements Cloneable {

    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected Thing clone() throws CloneNotSupportedException {
        Thing thing = null;
        try {
            thing = (Thing) super.clone();
            thing.arrayList = (ArrayList<String>) this.arrayList.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return thing;
    }

    public void setValue(String value) {
        this.arrayList.add(value);
    }

    public ArrayList<String> getValue() {
        return this.arrayList;
    }
}
