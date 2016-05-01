package org.hope6537.note.tij.eighteen;

import java.nio.ByteBuffer;

public class GetData {

    private static final int BSIZE = 1024;

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        int i = 0;
        while (i++ < buffer.limit()) {
            if (buffer.get() != 0) {
                System.out.println("nonZero");
            }
        }
        System.out.println("i= " + i);
        buffer.rewind();
        buffer.asCharBuffer().put("Howdy?!");
        char c;
        while ((c = buffer.getChar()) != 0) {
            System.out.print(c + " ");
        }
        System.out.println();
        // 每一次的rewind都是返回到数据开始部分
        buffer.rewind();
        buffer.asShortBuffer().put((short) 1234);
        System.out.println(buffer.getShort());
        buffer.rewind();
        // 下面的就不测试了 asClassBuffer().put(Class value);
        // 然后就是 .getClass() 最后rewind

    }
}
