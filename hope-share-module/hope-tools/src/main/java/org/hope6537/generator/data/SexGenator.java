package org.hope6537.generator.data;

/**
 * Created by hope6537 on 16/5/20.
 */
public class SexGenator {
    public static String generator() {

        if (Math.random() - 0.6 > 0) {
            return "男";
        } else {
            return "女";
        }

    }

}
