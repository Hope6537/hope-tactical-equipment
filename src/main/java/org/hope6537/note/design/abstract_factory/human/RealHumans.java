package org.hope6537.note.design.abstract_factory.human;

/** 
 * <p>Describe: 定义实体类型</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月10日下午2:21:08</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class RealHumans {

}

class FemaleYellowHuman extends AbstractYellows {
	@Override
	public void getSex() {
		System.out.println("Female");
	}
}

class MaleYellowHuman extends AbstractYellows {
	@Override
	public void getSex() {
		System.out.println("Male");
	}
}

class FemaleBlackHuman extends AbstractBlacks {
	@Override
	public void getSex() {
		System.out.println("Female");
	}
}

class MaleBlackHuman extends AbstractBlacks {
	@Override
	public void getSex() {
		System.out.println("Male");
	}
}

class FemaleWhiteHuman extends AbstractWhites {
	@Override
	public void getSex() {
		System.out.println("Female");
	}
}

class MaleWhiteHuman extends AbstractWhites {
	@Override
	public void getSex() {
		System.out.println("Male");
	}
}
