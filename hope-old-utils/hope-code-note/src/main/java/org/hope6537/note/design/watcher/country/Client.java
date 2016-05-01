package org.hope6537.note.design.watcher.country;

import org.junit.Test;

import java.util.Observer;

public class Client {

    @Test
    public void test() throws InterruptedException {

        Observer observer1 = new LiSi();
        Observer observer2 = new LiSi();


        HanFeiZi hanFeiZi = new HanFeiZi();
        hanFeiZi.addObserver(observer1);
        System.out.println("添加观察者");
        hanFeiZi.addObserver(observer2);
        System.out.println("添加观察者");


        hanFeiZi.haveBreakfast();
        hanFeiZi.haveFun();

    }
}
