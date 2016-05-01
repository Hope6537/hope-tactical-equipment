package org.hope6537.note.tij.eighteen;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 映射文件的部分加锁
 * @signdate 2014年7月25日上午11:07:55
 * @company Changchun University&SHXT
 */
public class LockingMappedFiles {

    /**
     * @describe 定义大小，通道，文件
     */
    static final int LENGTH = 0x8FFFFFF;
    static final File file = new File("G:\\LockingMappedFiles.dat");
    static FileChannel fc;

    public static void main(String[] args) throws Exception {
        // 声明通道类型，映射方式，同时写入文件，并调用部分加锁构造方法
        fc = new RandomAccessFile(file, "rw").getChannel();
        MappedByteBuffer out = fc
                .map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            out.put((byte) 'x');
        }
        new LockAndModify(out, 0, 0 + LENGTH / 3);
        new LockAndModify(out, LENGTH / 2, LENGTH / 2 + LENGTH / 4);
    }

    public static class LockAndModify extends Thread {
        // 在这里定义视图和视图读取的开始处和结束处
        private ByteBuffer buff;
        private int start, end;

        public LockAndModify(ByteBuffer buff, int start, int end) {
            this.start = start;
            this.end = end;
            buff.limit(end);
            buff.position(start);
            // buff.slice()是什么意思？ 答：用于修改缓冲区
            this.buff = buff.slice();
            // 然后直接线程启动
            start();
        }

        @Override
        public void run() {
            try {
                // 声明文件锁，并加载字节同时再+1写入回去
                FileLock fl = fc.lock(start, end, false);
                System.out.println("Locked : " + start + " to " + end);
                while (buff.position() < buff.limit() - 1) {
                    buff.put((byte) (buff.get() + 1));
                }
                fl.release();
                System.out.println("Release: " + start + " to " + end);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
