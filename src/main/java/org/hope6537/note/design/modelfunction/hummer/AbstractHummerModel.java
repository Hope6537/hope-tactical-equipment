package org.hope6537.note.design.modelfunction.hummer;

/** 
 *<pre>
 *</pre>
 * <p>Describe: 模板方法抽象实例类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月12日下午3:48:22</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public abstract class AbstractHummerModel {
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
	 * <p>Describe: run</p>
	 */
	public void run() {
		System.out.println(this.getClass().getSimpleName() + " run");
		start();
		engineboom();
		alarm();
		stop();
	}

	/**
	 * <p>Describe: engineboom</p>
	 */
	public abstract void engineboom();
}
