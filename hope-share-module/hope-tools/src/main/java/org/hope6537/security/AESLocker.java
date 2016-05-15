package org.hope6537.security;

import org.hope6537.exception.DecryptException;
import org.hope6537.exception.EncryptException;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESLocker {

    public static final int BASE64 = 0;
    public static final int HEX = 1;

    public static String encrypt(String data) {
        try {
            return encrypt(data, "xComicHentai6537", HEX);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptException("加密失败");
        }
    }

    public static String encryptBase64(String data) {
        try {
            return encrypt(data, "xComicHentai6537", BASE64);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EncryptException("加密失败");
        }
    }

    public static String decrypt(String data) {
        try {
            return decrypt(data, "xComicHentai6537", HEX);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DecryptException("解密失败");
        }
    }

    public static String decryptBase64(String data) {
        try {
            return decrypt(data, "xComicHentai6537", BASE64);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DecryptException("解密失败");
        }
    }

    private static String encrypt(String data, String key, int mode) throws Exception {

        try {
            if (key == null || key.length() != 16) {
                throw new EncryptException("密钥不合法");
            }
            String iv = "4798145623545678";
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
            switch (mode) {
                case BASE64:
                    return new sun.misc.BASE64Encoder().encode(encrypted);
                case HEX:
                    return ConvertUtils.asHex(encrypted);
                default:
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static String decrypt(String data, String key, int mode) throws Exception {
        try {
            if (key == null || key.length() != 16) {
                throw new DecryptException("密钥不合法");
            }
            String iv = "4798145623545678";
            byte[] encrypted1;
            switch (mode) {
                case BASE64:
                    encrypted1 = new BASE64Decoder().decodeBuffer(data);
                    break;
                case HEX:
                    encrypted1 = ConvertUtils.fromHex(data);
                    break;
                default:
                    return null;
            }
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}