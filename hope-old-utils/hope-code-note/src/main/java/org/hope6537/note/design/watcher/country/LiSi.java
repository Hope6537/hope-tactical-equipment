package org.hope6537.note.design.watcher.country;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

/**
 * 观察者实现类，使用JavaAPI接口
 */
public class LiSi implements Observer {


    private void report() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("汇报");
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("观察" + arg.toString());
            this.report();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
