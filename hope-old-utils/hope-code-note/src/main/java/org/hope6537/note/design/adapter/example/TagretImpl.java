package org.hope6537.note.design.adapter.example;

/**
 * 目标实现类
 */
public class TagretImpl implements Target {

    @Override
    public void request() {
        System.out.println("call");
    }

}
