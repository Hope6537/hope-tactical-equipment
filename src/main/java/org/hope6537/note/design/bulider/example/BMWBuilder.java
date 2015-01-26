package org.hope6537.note.design.bulider.example;

import java.util.ArrayList;

public class BMWBuilder extends AbstractCarBuilder {

	private BMWModel bmwModel = new BMWModel();

	@Override
	public void setSequence(ArrayList<String> list) {
		bmwModel.setOrderList(list);
	}

	@Override
	public AbstractCarModel getCarModel() {
		return bmwModel;
	}

}
