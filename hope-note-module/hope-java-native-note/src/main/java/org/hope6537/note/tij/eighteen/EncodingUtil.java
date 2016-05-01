package org.hope6537.note.tij.eighteen;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * <p>Describe: 文本文件编码转换器</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月14日下午8:05:40</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class EncodingUtil {

    public static void run(String directoryPath, String targetPath) {

        try {
            FileTreeInfo tree = walk(directoryPath);
            List<File> fileList = tree.files;
            for (File file : fileList) {
                GBKtoUTF(file.getAbsolutePath(), targetPath);
                System.out.println("*** [" + file.getName() + "] Finished!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out
                .println("The Input File Directory And the Output Directory ... Control + C is Exit");
        run(s.next(), s.next());
    }

    /**
     * <p>Describe: 遍历文件路径</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年7月26日下午1:47:32</p>
     * <p>Author:Hope6537</p>
     *
     * @param startDir
     * @param regex
     * @return
     * @see
     */
    static FileTreeInfo recurseDirs(File startDir, String regex) {
        FileTreeInfo result = new FileTreeInfo();
        // 对startDir出的list出来的目录进行遍历
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                // 如果是目录，那么添加进目录List
                result.dirs.add(item);
                // 同时接着将该目录开始的遍历之后的TreeInfo再合并
                result.addAll(recurseDirs(item, regex));
            } else {
                // 如果是文件，直接添加进文件List(前提，必须符合正则表达式)
                if (item.getName().matches(regex)) {
                    result.files.add(item);
                }
            }
        }
        // 最终返回目录树
        return result;
    }

    public static FileTreeInfo walk(String start) {
        return recurseDirs(new File(start), ".*");
    }

    /**
     * <pre>
     * 使用通道方法进行文件读取
     * <code>
     * FileChannel fc = new FileOutputStream(targetFile).getChannel();
     * </code>
     * 然后使用管道流的方法对文件进行按字节转换编码
     * 要注意这行代码
     * <code>
     * ByteBuffer.wrap(read(sourceFile.getAbsolutePath()).getBytes("UTF-8"));
     * </code>
     * 直接使用缓冲流类进行读取后写入
     * </pre>
     * <p>Describe: 将其他编码文件转化为UTF-8编码;</p>
     * <p>Using: 工具类</p>
     * <p>How To Work: 使用文件管道流</p>
     * <p>DevelopedTime: 2014年7月26日下午2:00:37 </p>
     * <p>Author:Hope6537</p>
     *
     * @param source 文件的字符串绝对路径
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @see
     */
    public static String GBKtoUTF(String source, String target)
            throws IOException, InterruptedException {
        // 确定临时缓存路径
        File folder = new File(target);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File sourceFile = new File(source).getAbsoluteFile();
        File targetFile = new File(folder.getAbsoluteFile() + "\\"
                + sourceFile.getName()).getAbsoluteFile();
        @SuppressWarnings("resource")
        FileChannel fc = new FileOutputStream(targetFile).getChannel();
        ByteBuffer messages = ByteBuffer
                .wrap(read(sourceFile.getAbsolutePath()).getBytes("UTF-8"));
        fc.write(messages);
        fc.close();
        return targetFile.getAbsolutePath();
    }

    /**
     * <p>Describe: 获得文件的字符串</p>
     * <p>Using: 工具方法</p>
     * <p>How To Work: 使用缓冲流进行读取</p>
     * <p>DevelopedTime: 2014年7月26日下午2:04:58</p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 文件的字符串绝对路径
     * @return 获得文本文件内容的字符串
     * @see
     */
    private static String read(String filename) {
        // 组建字符串
        StringBuilder sBuilder = new StringBuilder();
        try {
            // 缓冲区读入
            BufferedReader bufferedReader = new BufferedReader(new FileReader(
                    new File(filename).getAbsoluteFile()));
            try {
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    sBuilder.append(s);
                    sBuilder.append("\n");
                }
            } finally {
                // 要防止意外正确关闭
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.err.println("===读取文件异常===");
            e.printStackTrace();
        }
        // 最后返回字符串
        return sBuilder.toString();
    }

    /**
     * <p>Describe: 使用标准输出流对文件进行写入操作,注意该方法为<b>覆盖写入</b></p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年7月26日下午2:12:22 </p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 文件的绝对路径
     * @param text     待写入的内容
     * @return 是否全部写入成功
     * @see
     */
    @SuppressWarnings("unused")
    private static boolean write(String filename, String text) {
        boolean status = true;
        try {
            // 标准输出流
            PrintWriter out = new PrintWriter(
                    new File(filename).getAbsoluteFile());
            try {
                // 逐个字符串写入
                out.write(text);
            } finally {
                out.close();
            }
        } catch (IOException e) {
            status = false;
            System.out.println("===写入文件失败===");
            e.printStackTrace();
        }
        return status;
    }

    /**
     * <pre>
     * 通过使用迭代器的方法进行文件路径的遍历，最后生成可被容器识别的文件
     * </pre>
     * <p>Describe: 一个目录树的实体</p>
     * <p>Using: </p>
     * <p>DevelopedTime: 2014年7月23日下午3:10:39</p>
     * <p>Company: ChangChun Unviersity JiChuang Team</p>
     *
     * @author Hope6537
     * @version 1.0
     * @see
     */
    public static class TreeInfo implements Iterable<File> {
        /**
         * Describe: 该List保存文件
         */
        public List<File> files = new ArrayList<File>();
        /**
         * Describe: 该List保存目录
         */
        public List<File> dirs = new ArrayList<File>();

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        /**
         * <pre>
         *
         * </pre>
         * <p>Describe: 如果有另一个目录树，添加的例程</p>
         * <p>Using: </p>
         * <p>How To Work: </p>
         * <p>DevelopedTime: 2014年7月23日下午3:11:33 </p>
         * <p>Author:Hope6537</p>
         *
         * @param other
         * @see
         */
        void addAll(FileTreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

    }

    /**
     * <pre>
     * 通过使用迭代器的方法进行文件路径的遍历，最后生成可被容器识别的文件
     * </pre>
     * <p>Describe: 一个目录树的实体</p>
     * <p>Using: </p>
     * <p>DevelopedTime: 2014年7月23日下午3:10:39</p>
     * <p>Company: ChangChun Unviersity JiChuang Team</p>
     *
     * @author Hope6537
     * @version 1.0
     * @see
     */
    public static class FileTreeInfo implements Iterable<File> {
        /**
         * Describe: 该List保存文件
         */
        public List<File> files = new ArrayList<File>();
        /**
         * Describe: 该List保存目录
         */
        public List<File> dirs = new ArrayList<File>();

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        /**
         * <pre>
         *
         * </pre>
         * <p>Describe: 如果有另一个目录树，添加的例程</p>
         * <p>Using: </p>
         * <p>How To Work: </p>
         * <p>DevelopedTime: 2014年7月23日下午3:11:33 </p>
         * <p>Author:Hope6537</p>
         *
         * @param other
         * @see
         */
        void addAll(FileTreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

    }
}
