package org.hope6537.note.design.modelfunction.hummer;

/** 
 *<pre>
 *protected boolean isAlarm() {
 *	return false; <-- 这对这项，进行修改，父类在执行的时候，将会具体分析 
 *}
 *</pre>
 * <p>Describe: 修改后的模型2 不执行alarm方法</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月12日下午3:54:59</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class HummerH2Model_G extends HummerModel_G {

	@Override
	public void alarm() {
		System.out.println("h2 alarm");
	}

	@Override
	public void engineboom() {
		System.out.println("h2 engineboom");
	}

	/*
	 * 对于h1和h2模型来说，有共同的可以抽象出来的方法，所以都放在抽象类里定义
	 * 
	 * @Override public void run() { System.err.println("h1 test"); start();
	 * engineboom(); alarm(); stop(); }
	 */
	@Override
	public void stop() {
		System.out.println("h2 stop");
	}

	@Override
	public void start() {
		System.out.println("h2 start");
	}

	/*
	 * (non-Javadoc) 在这里进行了修改
	 * 
	 * @see org.hope6537.note.design.model.hummer.HummerModel_G#isAlarm()
	 * 
	 * @Change:Hope6537
	 */
	@Override
	protected boolean isAlarm() {
		return false;
	}

}
