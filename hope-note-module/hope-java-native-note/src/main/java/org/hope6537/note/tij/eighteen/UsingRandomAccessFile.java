package org.hope6537.note.tij.eighteen;

import java.io.IOException;
import java.io.RandomAccessFile;

public class UsingRandomAccessFile {

    static String file = "G:\\read.dat";

    static void display() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(file, "r");
        for (int i = 0; i < 7; i++) {
            System.out.println("Value" + i + ":" + accessFile.readDouble());
        }
        System.out.println(accessFile.readUTF());
        accessFile.close();
    }

    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
        for (int i = 0; i < 7; i++) {
            accessFile.writeDouble(1.1465 * i);
        }
        accessFile.writeUTF("\r\n===End of File===");
        accessFile.close();
        display();
    }

}
