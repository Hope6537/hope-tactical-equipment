package org.hope6537.note.tij.eighteen;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 格式化的内存输入
 * @signdate 2014年7月23日下午6:01:11
 * @company Changchun University&SHXT
 */
public class FormattedMemoryInput {

    public static void main(String[] args) throws IOException {
        try {
            String path = "E:\\(WorkSpace04)GitHub\\Project_00_Learning\\Project_0_Learning\\src\\org\\hope6537\\tij\\eighteen\\FormattedMemoryInput.java";
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(
                    BufferedInputFile.read(path).getBytes()));
            while (in.available() != 0/* true */) {//两种方法判定是否结束，一种直接调用，另一种靠异常捕获
                System.out.print((char) in.readByte());
            }
        } catch (Exception e) {
            System.out.println("===============End Of File===============");
        }
    }
}
