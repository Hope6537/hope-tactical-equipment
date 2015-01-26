package org.hope6537.note.design.factory.human;

/** 
 * <p>Describe: 白人的实现类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午3:31:01</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class WhiteHuman implements Human {

	@Override
	public void getColor() {
		System.out.println("白色的皮肤");
	}

	@Override
	public void talk() {
		System.out.println("Hello!");
	}

}
