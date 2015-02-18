package org.hope6537.note.design.bulider.example;

import java.util.ArrayList;

public class Director {

    private ArrayList<String> orderList = new ArrayList<String>();

    private BenzBuilder benzBuilder = new BenzBuilder();

    private BMWBuilder bmwBuilder = new BMWBuilder();

    public BenzModel getABenzModel() {
        this.orderList.clear();
        this.orderList.add("start");
        this.orderList.add("stop");
        this.benzBuilder.setSequence(orderList);
        return (BenzModel) this.benzBuilder.getCarModel();
    }

	/*
     * 下面的如上方法
	 */

}
