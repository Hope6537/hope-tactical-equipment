package org.hope6537.note.thinking_in_java.eighteen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class BufferToText {
	private static final int BSIZE = 1024;

	public static void main(String[] args) throws Exception {
		File file = new File("G:\\Data.txt").getAbsoluteFile();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		FileChannel fc = new FileOutputStream(file).getChannel();

		fc.write(ByteBuffer.wrap("Some Text".getBytes("UTF-8")));
		fc.close();
		fc = new FileInputStream(file).getChannel();

		fc.read(buffer);
		buffer.flip(); // 让其做好准备，FileChannel将会向ByteBuffer存储字节
		System.out.println(buffer.asCharBuffer());

		buffer.rewind();

		String encoding = System.getProperty("file.encoding");
		System.out.println("Decoding using " + encoding + " : "
				+ Charset.forName(encoding).decode(buffer));
		//获取文件管道
		fc = new FileOutputStream(file).getChannel();
		fc.write(ByteBuffer.wrap("Some Text".getBytes("UTF-16BE")));
		fc.close();
		fc = new FileInputStream(file).getChannel();
		buffer.clear();
		fc.read(buffer); // flip涓簑rite鍋氬噯澶�
		buffer.flip();
		System.out.println(buffer.asCharBuffer()); // 浣嗚繕鏄笉濂戒娇

		fc = new FileOutputStream(file).getChannel();
		buffer = ByteBuffer.allocate(24);
		buffer.asCharBuffer().put("Some Text");
		fc.write(buffer);
		fc.close();
		fc = new FileInputStream(file).getChannel();
		buffer.clear();
		fc.read(buffer);
		buffer.flip();
		System.out.println(buffer.asCharBuffer());

	}

}
