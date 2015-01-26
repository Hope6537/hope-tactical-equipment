package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;
import java.util.Random;

class Data implements Serializable {
	private static final long serialVersionUID = 4470902163791019135L;
	private int n;

	public Data(int n) {
		super();
		this.n = n;
	}

	@Override
	public String toString() {
		return n + "";
	}

}

/**
 * @describe 序列化对象的读写实验
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月25日下午4:04:43
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Worm implements Serializable {

	private static final long serialVersionUID = -3295485275133643264L;

	private static Random rand = new Random(47);

	private Data[] d = new Data[] { new Data(rand.nextInt(10)),
			new Data(rand.nextInt(10)), new Data(rand.nextInt(10)) };

	private Worm next;
	private char c;

	public Worm(int i, char x) {

		System.out.println("Worm 构造方法" + i);
		c = x;
		if (--i > 0) {
			next = new Worm(i, (char) (x + 1));
		}
	}

	public Worm() {
		System.out.println("Default");
	}

	@Override
	public String toString() {
		StringBuilder rBuilder = new StringBuilder("-->");
		rBuilder.append(c);
		rBuilder.append("(");
		for (Data dat : d) {
			rBuilder.append(dat);
		}
		rBuilder.append(")");
		if (next != null) {
			rBuilder.append(next);
		}
		return rBuilder.toString();
	}

	public static void main(String[] args) throws Exception {
		// 第一种方法 读写文件
		File file = new File("G:\\Worm.txt");
		Worm w = new Worm(6, 'a');
		System.out.println("w = " + w);
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				file));
		out.writeObject("Worm Storage\n");
		out.writeObject(w);
		out.flush();
		out.close();
		@SuppressWarnings("resource")
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		String s = (String) in.readObject();
		Worm w2 = (Worm) in.readObject();
		System.out.println(s + "w2 = " + w2);
		// 第二种方法 读写字符数组
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out2 = new ObjectOutputStream(bout);
		out2.writeObject("Worm storage\n");
		out2.writeObject(w);
		out2.flush();
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(
				bout.toByteArray()));
		s = (String) in2.readObject();
		Worm w3 = (Worm) in2.readObject();
		System.out.println(s + "w3 = " + w3);
	}

}
