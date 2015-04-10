package org.hope6537.note.design.watcher.country;

import org.junit.Test;

import java.util.Observer;

public class Client {

    @Test
    public void test() {

        Observer observer1 = new LiSi();
        Observer observer2 = new LiSi();

        HanFeiZi hanFeiZi = new HanFeiZi();
        hanFeiZi.addObserver(observer1);
        hanFeiZi.addObserver(observer2);

        hanFeiZi.haveBreakfast();
        hanFeiZi.haveFun();

    }
}
