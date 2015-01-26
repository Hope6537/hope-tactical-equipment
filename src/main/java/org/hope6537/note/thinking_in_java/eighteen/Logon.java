package org.hope6537.note.thinking_in_java.eighteen;

//: io/Logon.java
//Demonstrates the "transient" keyword.

import java.io.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @describe 使用transient关键字
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月25日下午4:55:45
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Logon implements Serializable {
	private static final long serialVersionUID = -8426369763756174537L;
	private Date date = new Date();
	private String username;
	private transient String password;

	public Logon(String name, String pwd) {
		username = name;
		password = pwd;
	}

	public String toString() {
		return "logon info: \n   username: " + username + "\n   date: " + date
				+ "\n   password: " + password;
	}

	public static void main(String[] args) throws Exception {
		Logon a = new Logon("Hulk", "myLittlePony");
		System.out.println("logon a = " + a);
		ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(
				"G:\\Logon.out"));
		o.writeObject(a);
		o.close();
		TimeUnit.SECONDS.sleep(1); // 延迟
		// 从流中获取
		@SuppressWarnings("resource")
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				"G:\\Logon.out"));
		System.out.println("Recovering object at " + new Date());
		a = (Logon) in.readObject();
		//回来的时候我们发现transient关键字里的信息消失了
		System.out.println("logon a = " + a);
	}
}
