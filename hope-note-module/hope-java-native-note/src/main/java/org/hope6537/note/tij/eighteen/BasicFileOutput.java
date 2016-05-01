package org.hope6537.note.tij.eighteen;

import java.io.*;

public class BasicFileOutput {
    static String file = "C:\\Users\\Zhaopeng-Rabook\\Desktop\\Google Download\\计组杂物\\BasicFileOutput.out";
    static String path = "E:\\(WorkSpace04)GitHub\\Project_00_Learning\\Project_0_Learning\\src\\org\\hope6537\\tij\\eighteen\\BasicFileOutput.java";

    /**
     * @param args
     * @throws IOException
     * @descirbe 基本的文件输出
     * @author Hope6537(赵鹏)
     * @signDate 2014年7月23日下午6:12:52
     * @version 0.9
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new StringReader(
                BufferedInputFile.read(path)));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
                file)));
        int linecount = 1;
        String s;
        while ((s = in.readLine()) != null) {
            out.println(linecount++ + ": " + s);
        }
        out.close();
        System.out.println(BufferedInputFile.read(file));
    }
}
