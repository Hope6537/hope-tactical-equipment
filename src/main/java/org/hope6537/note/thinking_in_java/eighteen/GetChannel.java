package org.hope6537.note.thinking_in_java.eighteen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class GetChannel {

    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        FileChannel fc = new FileOutputStream("G:\\Data.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some Text".getBytes()));
        fc.close();
        fc = new RandomAccessFile("G:\\Data.txt", "rw").getChannel();
        fc.position(fc.size());
        fc.write(ByteBuffer.wrap("\nSome More".getBytes()));
        fc.close();
        fc = new FileInputStream("G:\\Data.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(BSIZE);
        fc.read(buff);
        buff.flip();
        while (buff.hasRemaining()) {
            System.out.print((char) buff.get());
        }
    }
}
