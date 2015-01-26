package org.hope6537.note.design.factory.replace;

import java.lang.reflect.Constructor;

/** 
 * <p>Describe: 负责生产单例的工厂类</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月9日下午3:15:26</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class SingleTonFactory {

	private static SingleTon singleTon;

	static {
		try {
			Class cl = Class.forName(SingleTon.class.getName());
			@SuppressWarnings("unchecked")
			// 选择无参构造器
			Constructor constructor = cl.getDeclaredConstructor();
			// 生产一个实例对象
			constructor.setAccessible(true);
			singleTon = (SingleTon) constructor.newInstance();
		} catch (Exception e) {
			System.out.println("创建对象失败");
			e.printStackTrace();
		}
	}

	public static SingleTon getSingleTon() {
		return singleTon;
	}
}
