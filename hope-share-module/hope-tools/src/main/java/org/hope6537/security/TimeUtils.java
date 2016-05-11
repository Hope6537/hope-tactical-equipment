package org.hope6537.security;

/**
 * Created by hope6537 on 16/3/17.
 */
public class TimeUtils {

    public static int getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

}
