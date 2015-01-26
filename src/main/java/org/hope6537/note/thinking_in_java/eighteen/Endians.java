package org.hope6537.note.thinking_in_java.eighteen;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @describe 通过存放模式改变字符中的字节次序
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月24日下午7:39:17
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class Endians {

	public static void main(String[] args) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
		bb.asCharBuffer().put("abcdef");
		display(bb);
		bb.rewind();
		bb.order(ByteOrder.BIG_ENDIAN);// 默认为高位优先
		bb.asCharBuffer().put("abcdef");
		display(bb);
		bb.rewind();
		bb.order(ByteOrder.LITTLE_ENDIAN); //低位优先存储
		bb.asCharBuffer().put("abcdef");
		display(bb);
	}

	static void display(ByteBuffer bb) {
		System.out.println(Arrays.toString(bb.array()));
	}
}
