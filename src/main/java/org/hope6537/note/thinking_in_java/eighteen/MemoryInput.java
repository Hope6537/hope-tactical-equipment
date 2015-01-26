package org.hope6537.note.thinking_in_java.eighteen;

import java.io.IOException;
import java.io.StringReader;

/**
 * @describe 从内存输入
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月23日下午6:00:23
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class MemoryInput {

	public static void main(String[] args) throws IOException {
		String path = "E:\\(WorkSpace04)GitHub\\Project_00_Learning\\Project_0_Learning\\src\\org\\hope6537\\thinking_in_java\\eighteen\\MemoryInput.java";
		StringReader in = new StringReader(
				BufferedInputFile.read(path));
		int c;
		while ((c = in.read()) != -1) {
			System.out.print((char) c);
		}
	}

}
