package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 映射文件访问的速度性比较
 * @signdate 2014年7月24日下午8:20:51
 * @company Changchun University&SHXT
 */
public class MappedIO {
    private static File file = new File("G:\\File.dat");
    private static int numOfInts = 4000000;
    private static int numOfUbuffInts = 200000;
    private static Tester[] tests = {new Tester("Stream Write") {
        public void test() throws IOException {
            DataOutputStream dos = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file)));
            for (int i = 0; i < numOfInts; i++)
                dos.writeInt(i);
            dos.close();
        }
    }, new Tester("Mapped Write") {
        public void test() throws IOException {
            FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
            IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size())
                    .asIntBuffer();
            for (int i = 0; i < numOfInts; i++)
                ib.put(i);
            fc.close();
        }
    }, new Tester("Stream Read") {
        public void test() throws IOException {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(
                    new FileInputStream(file)));
            for (int i = 0; i < numOfInts; i++)
                dis.readInt();
            dis.close();
        }
    }, new Tester("Mapped Read") {
        public void test() throws IOException {
            FileChannel fc = new FileInputStream(file).getChannel();
            IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size())
                    .asIntBuffer();
            while (ib.hasRemaining())
                ib.get();
            fc.close();
        }
    }, new Tester("Stream Read/Write") {
        public void test() throws IOException {
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.writeInt(1);
            for (int i = 0; i < numOfUbuffInts; i++) {
                raf.seek(raf.length() - 4);
                raf.writeInt(raf.readInt());
            }
            raf.close();
        }
    }, new Tester("Mapped Read/Write") {
        public void test() throws IOException {
            FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
            IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size())
                    .asIntBuffer();
            ib.put(0);
            for (int i = 1; i < numOfUbuffInts; i++)
                ib.put(ib.get(i - 1));
            fc.close();
        }
    }};

    public static void main(String[] args) {
        for (Tester test : tests)
            test.runTest();
    }

    private abstract static class Tester {
        private String name;

        public Tester(String name) {
            this.name = name;
        }

        public void runTest() {
            System.out.print(name + ": ");
            try {
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() - start;
                System.out.format("%.2f\n", duration / 1.0e9);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public abstract void test() throws IOException;
    }
} /*
 * Output: (90% match) Stream Write: 0.56 Mapped Write: 0.12 Stream Read: 0.80
 * Mapped Read: 0.07 Stream Read/Write: 5.32 Mapped Read/Write: 0.02
 */// :~
