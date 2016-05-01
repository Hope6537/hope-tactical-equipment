package org.hope6537.note.tij.eighteen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 通道相连
 * @signdate 2014年7月24日下午5:15:51
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
