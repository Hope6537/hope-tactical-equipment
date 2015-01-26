package org.hope6537.note.design.factory.human;

import java.util.ArrayList;

/** 
 * <p>Describe: 工厂测试</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午3:44:43</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class Client {

	public static void main(String[] args) {

		AbstractHumanFactory factory = new HumanFactory();

		ArrayList<Human> list = new ArrayList<Human>();
		Human whiteHuman = factory.createHuman(WhiteHuman.class);
		Human blackHuman = factory.createHuman(BlackHuman.class);
		Human yellowHuman = factory.createHuman(YellowHuman.class);
		list.add(whiteHuman);
		list.add(blackHuman);
		list.add(yellowHuman);
		for(Human h : list){
			h.getColor();
			h.talk();
		}
		
	}

}
