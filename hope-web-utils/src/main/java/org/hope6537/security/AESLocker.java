package org.hope6537.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESLocker {


    private static String Decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                // sKey = "Hope6537JiChuang";
                sKey = "2014-11-09 16:18";
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }

            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    /**
     * <p>Describe: 对字符串进行解密操作</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年10月12日下午3:20:35 </p>
     * <p>Author:Hope6537</p>
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     * @see
     */
    public static String Decrypt(String sSrc) {
        return Decrypt(sSrc, null);
    }

    private static String Encrypt(String sSrc, String sKey) {
        if (sKey == null) {
            // sKey = "Hope6537JiChuang";
            sKey = "2014-11-09 16:18";
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            return byte2hex(encrypted).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * <p>Describe: 对字符串进行加密操作</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年10月12日下午3:20:15 </p>
     * <p>Author:Hope6537</p>
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     * @see
     */
    public static String Encrypt(String sSrc) {
        return Encrypt(sSrc, null);
    }

    private static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}
