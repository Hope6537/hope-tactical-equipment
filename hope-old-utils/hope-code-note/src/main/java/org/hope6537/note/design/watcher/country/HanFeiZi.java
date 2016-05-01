package org.hope6537.note.design.watcher.country;

import java.util.Observable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 被观察者实现类，使用Java自带API
 */
public class HanFeiZi extends Observable implements IHanFeiZi {

    @Override
    public void haveBreakfast() throws InterruptedException {
        System.out.println("开始吃饭");
        setChanged();
        TimeUnit.SECONDS.sleep(3);
        notifyObservers("他在吃饭");
    }

    @Override
    public void haveFun() throws InterruptedException {
        System.out.println("开始娱乐");
        setChanged();
        TimeUnit.SECONDS.sleep(3);
        notifyObservers("他在娱乐");
    }
}
