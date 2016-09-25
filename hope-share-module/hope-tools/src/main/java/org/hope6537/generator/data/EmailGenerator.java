package org.hope6537.generator.data;

import java.util.UUID;

/**
 * 邮箱生成器
 * Created by hope6537 on 16/5/19.
 */
public class EmailGenerator {

    public static String generator() {
        return UUID.randomUUID().toString().split("-")[0] + "@hope6537.com";
    }
}
