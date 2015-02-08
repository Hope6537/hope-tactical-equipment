package org.hope6537.note.design.bulider.example;

public class BMWModel extends AbstractCarModel {

    @Override
    protected void start() {
        System.out.println(this.getClass().getSimpleName() + "start");
    }

    @Override
    protected void stop() {
        System.out.println(this.getClass().getSimpleName() + "stop");
    }

    @Override
    protected void alarm() {
        System.out.println(this.getClass().getSimpleName() + "alarm");
    }

    @Override
    protected void engineBoom() {
        System.out.println(this.getClass().getSimpleName() + "engineBoom");
    }

}
