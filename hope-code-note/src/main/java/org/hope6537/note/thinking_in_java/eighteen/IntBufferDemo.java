package org.hope6537.note.thinking_in_java.eighteen;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 视图缓冲器
 * @signdate 2014年7月24日下午7:33:01
 * @company Changchun University&SHXT
 */
public class IntBufferDemo {

    private static final int BSIZE = 1024;

    public static void main(String[] args) {

        ByteBuffer bb = ByteBuffer.allocate(BSIZE);
        IntBuffer ib = bb.asIntBuffer();//使用缓冲的Int视图来确定int数据
        ib.put(new int[]{11, 42, 33, 55, 77, 99, 97});
        System.out.println(ib.get(3));
        ib.put(3, 1444);
        ib.flip();
        while (ib.hasRemaining()) {
            int i = ib.get();
            System.out.println(i);
        }

    }

}
