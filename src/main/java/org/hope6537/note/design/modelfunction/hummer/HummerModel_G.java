package org.hope6537.note.design.modelfunction.hummer;

/** 
 *<pre>
 *主要是修改了run方法的执行流程
 *</pre>
 * <p>Describe: 修改后的悍马模型</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月12日下午3:50:09</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public abstract class HummerModel_G {
	/**
	 * <p>Describe: start</p>
	 */
	public abstract void start();

	/**
	 * <p>Describe: stop</p>
	 */
	public abstract void stop();

	/**
	 * <p>Describe: alarm</p>
	 */
	public abstract void alarm();

	/**
	 * <p>Describe: 主要在这里更改了业务流程</p>
	 */
	public void run() {
		System.out.println(this.getClass().getSimpleName() + " run");
		start();
		engineboom();
		if (isAlarm()) {
			alarm();
		}
		stop();
	}

	/**
	 * <p>Describe: engineboom</p>
	 */
	public abstract void engineboom();

	protected boolean isAlarm() {
		return true;
	}
}
