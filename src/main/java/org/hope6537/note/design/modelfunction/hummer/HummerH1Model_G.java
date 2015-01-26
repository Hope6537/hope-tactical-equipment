package org.hope6537.note.design.modelfunction.hummer;

/** 
 * <p>Describe: H1型号的模型</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月12日下午3:19:41</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class HummerH1Model_G extends HummerModel_G {

	@Override
	public void alarm() {
		System.out.println("h1 alarm");
	}

	@Override
	public void engineboom() {
		System.out.println("h1 engineboom");
	}

	/*
	 * 对于h1和h2模型来说，有共同的可以抽象出来的方法，所以都放在抽象类里定义
	 * 
	 * @Override public void run() { System.err.println("h1 test"); start();
	 * engineboom(); alarm(); stop(); }
	 */
	@Override
	public void stop() {
		System.out.println("h1 stop");
	}

	@Override
	public void start() {
		System.out.println("h1 start");
	}
	

}
