package org.hope6537.note.leetcode;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/3/30.
 */
public class ReverseInteger {
    public int reverse(int x) {
        long r = 0;
        for (; x != 0; x /= 10) {
            r = r * 10 + x % 10;
        }
        return Math.abs(r) > Integer.MAX_VALUE ? 0 : (int) r;
    }

    @Test
    public void test() {
        System.out.println(reverse(-2147483648));
    }
}
