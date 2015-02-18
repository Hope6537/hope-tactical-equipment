package org.hope6537.note.design.modelfunction.hummer;

public class HummerH2Model extends AbstractHummerModel {

    @Override
    public void alarm() {
        System.out.println("h2 alarm");
    }

    @Override
    public void engineboom() {
        System.out.println("h2 engineboom");
    }

    /*
     * 对于h1和h2模型来说，有共同的可以抽象出来的方法，所以都放在抽象类里定义
     *
     * @Override public void run() { System.err.println("h1 test"); start();
     * engineboom(); alarm(); stop(); }
     */
    @Override
    public void stop() {
        System.out.println("h2 stop");
    }

    @Override
    public void start() {
        System.out.println("h2 start");
    }

}
