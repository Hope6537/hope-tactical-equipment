package org.hope6537.generator.data;

import java.util.Random;

/**
 * 电话号码生成器
 * Created by hope6537 on 16/5/19.
 */
public class TelGenerator {

    final static String[] prefix = new String[]{"133", "134", "135", "138", "186", "188"};

    public static String generateTel() {
        Random rand = new Random();
        String tel = prefix[rand.nextInt(prefix.length - 1)];
        for (int i = 0; i < 8; i++) {
            tel += rand.nextInt(10);
        }
        return tel;
    }

}
