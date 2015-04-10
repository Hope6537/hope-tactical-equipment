package org.hope6537.note.design.watcher.country;

import java.util.Observable;

/**
 * 被观察者实现类，使用Java自带API
 */
public class HanFeiZi extends Observable implements IHanFeiZi {

    @Override
    public void haveBreakfast() {
        System.out.println("开始吃饭");
        setChanged();
        notifyObservers("他在吃饭");
    }

    @Override
    public void haveFun() {
        System.out.println("开始娱乐");
        setChanged();
        notifyObservers("他在娱乐");
    }
}
