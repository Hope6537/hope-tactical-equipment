package org.hope6537.note.design.bulider.example;

import java.util.ArrayList;

public class BenzBuilder extends AbstractCarBuilder {

    private BenzModel benzModel = new BenzModel();

    @Override
    public void setSequence(ArrayList<String> list) {
        benzModel.setOrderList(list);
    }

    @Override
    public AbstractCarModel getCarModel() {
        return benzModel;
    }


}
