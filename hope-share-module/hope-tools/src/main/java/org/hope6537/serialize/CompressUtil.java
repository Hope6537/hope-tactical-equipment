package org.hope6537.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author wuyang.zp
 */
public abstract class CompressUtil {

    private static final int BUFFER_SIZE = 256;

    public static byte[] zip(byte[] input) {
        if (input == null || input.length == 0) {
            return input;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(input);
            gzip.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] unzip(byte[] output) {
        if (output == null || output.length == 0) {
            return output;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(output);
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[BUFFER_SIZE];
            int ret;
            while ((ret = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, ret);
            }
            gunzip.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
