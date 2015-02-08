package org.hope6537.file;

import java.io.*;

/**
 * @version 0.9
 * @Describe 关于文件的操作
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-29下午06:23:16
 * @company Changchun University&SHXT
 */
public class FileByteUtil {

    /**
     * @Descirbe 文件转化成数组
     * @Author Hope6537(赵鹏)
     * @Params @param file
     * @Params @return
     * @SignDate 2014-4-29下午06:23:31
     * @Version 0.9
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                // log.error("helper:the file is null!");
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            // log.error("helper:get bytes from file process error!");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * @Descirbe 字符数组转化成文件
     * @Author Hope6537(赵鹏)
     * @Params @param b
     * @Params @param outputFile
     * @Params @return
     * @SignDate 2014-4-29下午06:23:58
     * @Version 0.9
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            // log.error("helper:get file from byte process error!");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // log.error("helper:get file from byte process error!");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
