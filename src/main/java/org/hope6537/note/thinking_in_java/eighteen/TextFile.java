package org.hope6537.note.thinking_in_java.eighteen;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * <p>Describe: 读写文件的工具类,通过该工具类，可以使用标准I/O进行文件的基本读写</p>
 * <p>Using: System.out.println();</p>
 * <p>DevelopedTime: 2014年7月24日上午10:19:51</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see java.util.Scanner
 */
public class TextFile extends ArrayList<String> {

    private static final long serialVersionUID = -7716867579398514830L;

    /**
     * <p>Describe: 获得文件的字符串</p>
     * <p>Using: 用于从文件中获取信息，并生成字符串，供其他方法使用</p>
     * <p>How To Work: 使用缓冲器进行读操作</p>
     * <p>DevelopedTime: 2014年7月24日上午10:20:01</p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 目标文件的绝对路径
     * @return sBuilder.toString() 文本的String对象
     * @see java.util.Scanner
     */
    public static String read(String filename) {
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
     * <p>Describe: 将字符串写入文件</p>
     * <p>Using: 用于I/O写操作</p>
     * <p>How To Work: 使用PrintWriter进行write操作，当然需要刷新和关闭流</p>
     * <p>DevelopedTime: 2014年7月24日上午10:20:46</p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 目标文件的绝对路径
     * @param text     要写入的文本
     * @return status 标记写入文件的状态
     * @see java.util.Scanner
     */
    public static boolean write(String filename, String text) {
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
     * <p>Describe: 直接获取文件和分隔符。形成可读的ArrayList<p>
     *
     * @param filename 定义文件名称
     * @param splitter 定义分割号名称
     */
    public TextFile(String filename, String splitter) {
        super(Arrays.asList(read(filename).split(splitter)));
        // 紧接着我们得到了一个按照分割符排列的ArrayList 通过get(index)即可调用
        // 消除第一个空白行
        if (get(0).equals("")) {
            remove(0);
        }
    }

    /**
     * <p>Describe: 直接获取文件，分割符使用空格代替。形成可读的ArrayList<p>
     *
     * @param filename 定义文件名称
     */
    public TextFile(String filename) {
        this(filename, "\n");
    }

    /**
     * <p>Describe: 将字符串写入文件</p>
     * <p>Using: 用于I/O写操作</p>
     * <p>How To Work: 使用PrintWriter进行write操作，当然需要刷新和关闭流</p>
     * <p>DevelopedTime: 2014年7月24日上午10:20:46</p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 目标文件的绝对路径
     * @param text     要写入的文本
     * @return status 标记写入文件的状态
     * @see java.util.Scanner
     */
    public static boolean write(String filename, String text) {
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
    }    /**
     * <p>Describe: 默认构造方法 <p>
     */
    public TextFile() {
    }

    public static void main(String[] args) {
        String sourcename = "E:\\(WorkSpace04)GitHub\\Project_00_Learning\\Project_0_Learning\\src\\org\\hope6537\\thinking_in_java\\eighteen\\TextFile.java";
        String filename = "G:\\Data2.txt";
        @SuppressWarnings("unused")
        String filename2 = "G:\\Data3.txt";
        String file = read(sourcename);
        write(filename, file);
        TextFile textFile = new TextFile(filename);
        textFile.write(filename);
        TreeSet<String> words = new TreeSet<String>(new TextFile(sourcename,
                "\\W+"));
        System.out.println(words.headSet("a"));
    }

    /**
     * <p>Describe: 将持有的ArrayList写入到文件中去 相当于复制粘贴</p>
     * <p>Using: </p>
     * <p>How To Work: 目标文件的绝对路径</p>
     * <p>DevelopedTime: 2014年7月24日上午10:23:09</p>
     * <p>Author:Hope6537</p>
     *
     * @param filename 指定文件定义的绝对路径
     * @see java.util.Scanner
     */
    public void write(String filename) {
        try {
            PrintWriter out = new PrintWriter(
                    new File(filename).getAbsoluteFile());
            try {
                // 調用本身ArrayList的迭代器
                for (String item : this) {
                    out.println(item);
                }

            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("===写入文件失败===");
            e.printStackTrace();
        }
    }
}
