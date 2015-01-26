package org.hope6537.note.design.factory.human;

/** 
 * <p>Describe: 黄种人的实现类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月5日下午3:29:50</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class YellowHuman implements Human {

	/* (non-Javadoc)
	 * @see org.hope6537.note.design.classicfactory.Human#getColor()
	 * @Change:Hope6537
	 */
	@Override
	public void getColor() {

		System.out.println("黄色的皮肤");
	}

	/* (non-Javadoc)
	 * @see org.hope6537.note.design.classicfactory.Human#talk()
	 * @Change:Hope6537
	 */
	@Override
	public void talk() {

		System.out.println("你好");
		
	}

}
