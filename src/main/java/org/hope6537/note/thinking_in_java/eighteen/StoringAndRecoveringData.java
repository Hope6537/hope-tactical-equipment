package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;

public class StoringAndRecoveringData {

    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(
                new FileOutputStream("D:\\Data.txt")));
        out.writeDouble(3.1415926);
        out.writeUTF("That was pi");
        //必须关闭流，缓冲中的数据才能写入
        out.close();
        @SuppressWarnings("resource")
        DataInputStream in = new DataInputStream(new BufferedInputStream(
                new FileInputStream("D:\\Data.txt")));
        System.out.println(in.readDouble());
        System.out.println(in.readUTF());
    }
}
