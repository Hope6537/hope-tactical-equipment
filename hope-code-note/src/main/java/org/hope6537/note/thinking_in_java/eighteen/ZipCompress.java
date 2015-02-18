package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使用Zip进行压缩
 * @signdate 2014年7月25日下午1:57:52
 * @company Changchun University&SHXT
 */
public class ZipCompress {

    public static void main(String[] args) {
        try {
            Compress(new String[]{"G:\\Data.txt"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("resource")
    public static void Compress(String[] args) throws IOException {
        File file = new File("G:\\ZipCompress.zip");
        FileOutputStream f = new FileOutputStream(file);
        // 定义压缩格式 定义压缩文件输出流 定义緩衝输出流 定义注释
        CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        zos.setComment("Java Zipping : Powered By Hope6537");
        // 然后开始提取出文件名字段
        for (String arg : args) {
            System.out.println("Writing texts :" + arg);
            // 获取输入流
            BufferedReader in = new BufferedReader(new FileReader(arg));
            // 壓縮文件輸出流添加新的ZipEntry对象，該对象將用於获取和设置该压缩文件内所有可以利用的数据
            zos.putNextEntry(new ZipEntry(arg));
            int c;
            // 进行边读边写
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.flush();
        }
        out.close();
        // 然后关闭流 压缩结束

        System.out.println("CheckedOutputStream "
                + csum.getChecksum().getValue());
        System.out.println("====Reading Files====");
        // 读取压缩文件 同上 定义文件读取流 压缩文件读取流 缓冲读取流
        FileInputStream fi2 = new FileInputStream(file);
        CheckedInputStream cusmi = new CheckedInputStream(fi2, new Adler32());
        ZipInputStream in = new ZipInputStream(cusmi);
        BufferedInputStream bis = new BufferedInputStream(in);
        ZipEntry ze;
        // 如果还有压缩对象，提取出来然后开始按照字节读
        while ((ze = in.getNextEntry()) != null) {
            System.out.println("Reading File :" + ze);
            int x;
            while ((x = bis.read()) != -1) {
                System.out.write(x);
            }
        }
        System.out.println();
        if (args.length == 1) {
            System.out.println("CheckSum :" + cusmi.getChecksum().getValue());
        }
        bis.close();
        // 另外一种解压缩方法
        ZipFile zf = new ZipFile(file);
        @SuppressWarnings("rawtypes")
        Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            System.out.println("File: " + ze2);

        }
    }
}
