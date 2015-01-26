package org.hope6537.note.design.factory.simple;

import org.hope6537.note.design.factory.human.Human;
import org.hope6537.note.design.factory.human.WhiteHuman;

public class NvWa {

	public static void main(String[] args) {
		System.out.println("First");
		Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
		whiteHuman.getColor();
		whiteHuman.talk();
	}

}
