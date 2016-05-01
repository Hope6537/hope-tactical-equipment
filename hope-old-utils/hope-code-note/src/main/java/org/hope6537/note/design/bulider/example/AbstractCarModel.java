package org.hope6537.note.design.bulider.example;

import java.util.ArrayList;

public abstract class AbstractCarModel {

    private ArrayList<String> orderList = new ArrayList<String>();

    protected abstract void start();

    protected abstract void stop();

    protected abstract void alarm();

    protected abstract void engineBoom();

    protected void run() {
        for (String order : orderList) {
            switch (order) {
                case "start":
                    this.start();
                    break;
                case "stop":
                    this.stop();
                    break;
                case "alarm":
                    this.alarm();
                    break;
                case "engineBoom":
                    this.engineBoom();
                    break;
                default:
                    System.err.println("Not Match");
                    break;
            }
        }
    }

    final public void setOrderList(ArrayList<String> orderList) {
        this.orderList = orderList;
    }

}
