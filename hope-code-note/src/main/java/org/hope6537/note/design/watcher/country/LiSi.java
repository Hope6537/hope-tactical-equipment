package org.hope6537.note.design.watcher.country;

import java.util.Observable;
import java.util.Observer;

/**
 * 观察者实现类，使用JavaAPI接口
 */
public class LiSi implements Observer {


    private void report() {
        System.out.println("汇报");
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("观察" + arg.toString());
        this.report();
    }

}
