package org.hope6537.note.thinking_in_java.eighteen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 很普通的读入文件
 * @signdate 2014年7月23日下午5:52:59
 * @company Changchun University&SHXT
 */
public class BufferedInputFile {

    /**
     * @param filename
     * @return
     * @throws java.io.IOException
     * @descirbe 读入文件 产生字符串
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午5:53:09
     * @version 0.9
     */
    public static String read(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        StringBuilder builder = new StringBuilder();
        while ((s = in.readLine()) != null) {
            builder.append(s + "\n");
        }
        in.close();
        return builder.toString();
    }

    public static void main(String[] args) {
        try {
            String path2 = "C:\\Users\\Zhaopeng-Rabook\\Desktop\\Google Download\\计组杂物\\mul.vhd";
            System.out.println(read(path2));
            //.println(read("E:\\(WorkSpace04)GitHub\\Project_00_Learning\\Project_0_Learning\\src\\org\\hope6537\\thinking_in_java\\eighteen\\BufferedInputFile.java"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
