package org.hope6537.note.thinking_in_java.eighteen;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @describe 模拟大文件读写
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月24日下午8:15:42
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class LargeMappedFiles {

	static int length = 0x8FFFFFF;

	public static void main(String[] args) throws Exception {
		String filename = "G:\\Data.dat";
		// 这是在RandomAccessFile获得文件上的通道，然后调用map的MappedByteBuffer,这是一种特殊类型的直接缓冲器
		// 指定映射文件的初始位置和长度，则我们可以获得该文件中我们想要的较小的一部分
		MappedByteBuffer out = new RandomAccessFile(filename, "rw")
				.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, length);
		for (int i = 0; i < length; i++) {
			out.put((byte) 'x');
		}
		System.out.println("finish writing");
		for (int i = length / 2; i < length / 2 + 6; i++) {
			System.out.println((char) (out.get(i)));
		}

	}

}
