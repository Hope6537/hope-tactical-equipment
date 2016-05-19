package org.hope6537.generator.data;

import java.util.Random;

/**
 * Created by hope6537 on 16/5/20.
 */
public class AddressGenerator {

    public static String simpleGenerator() {
        Random random = new Random();
        return "吉林省长春市双阳区第" + random.nextInt(20) + "街区第" + random.nextInt(99) + "栋";
    }
}
