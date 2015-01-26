package org.hope6537.note.thinking_in_java.eighteen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @describe 通道相连
 * @author Hope6537(赵鹏)
 * @signdate 2014年7月24日下午5:15:51
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class TransferTo {

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("arguements : sourcefile destfile");
			System.exit(1);
		}
		@SuppressWarnings("resource")
		FileChannel in = new FileInputStream(args[0]).getChannel(), out = new FileOutputStream(
				args[1]).getChannel();

		in.transferTo(0, in.size(), out);
		// 接着是
		out.transferFrom(in, 0, in.size());
		// 两者皆可
	}

}
