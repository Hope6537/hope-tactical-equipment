package org.hope6537.note.design.bulider.example;

import org.junit.Test;

import java.util.ArrayList;

public class Client {

	@Test
	public void test(){
		ArrayList<String> orderList = new ArrayList<String>();
		orderList.add("start");
		orderList.add("alarm");
		BenzBuilder builder = new BenzBuilder();
		builder.setSequence(orderList);
		BenzModel benzModel = (BenzModel) builder.getCarModel();
		benzModel.run();
	}
	
}
