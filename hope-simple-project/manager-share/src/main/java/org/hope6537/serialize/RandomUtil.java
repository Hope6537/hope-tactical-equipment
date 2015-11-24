/*
 * Copyright 1999-2101 Alibaba.com. All rights reserved.
 * This software is the confidential and proprietary information of Alibaba.com ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance with the terms of
 * the license agreement you entered into with Alibaba.com.
 */
package org.hope6537.serialize;

import java.security.SecureRandom;

/**
 * @author ZZ
 */
public class RandomUtil {

    private static final SecureRandom random = new SecureRandom();

    public static int nextInt() {
        return random.nextInt();
    }

    public static int nextInt(int value) {
        return random.nextInt(value);
    }

    public static long nextLong() {
        return random.nextLong();
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    public static float nextFloat() {
        return random.nextFloat();
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static void nextBytes(byte[] bytes) {
        random.nextBytes(bytes);
    }

    public static double nextGaussian() {
        return random.nextGaussian();
    }

}
