package org.hope6537.security;

import org.junit.Test;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESLocker {

    public static String encrypt(String data) {
        try {
            return encrypt(data, "Hope6537JiChuang");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String data) {
        try {
            return decrypt(data, "Hope6537JiChuang");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String encrypt(String data, String key) throws Exception {
        try {
            if (key == null || key.length() != 16) {
                throw new RuntimeException("key error");
            }
            String iv = "1234567812345678";
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String decrypt(String data, String key) throws Exception {
        try {
            if (key == null || key.length() != 16) {
                throw new RuntimeException("key error");
            }
            String iv = "1234567812345678";
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original).trim();
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Test
    public void testDecrypt() {
        System.out.println(decrypt("Q3uKBgPV28Du3/JmWir12Q=="));
    }
}