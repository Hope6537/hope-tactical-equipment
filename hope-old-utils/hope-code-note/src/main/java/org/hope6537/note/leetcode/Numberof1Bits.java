package org.hope6537.note.leetcode;

import org.junit.Test;

public class Numberof1Bits {
    public int hammingWeight(int n) {
        char[] s = Integer.toBinaryString(n).toCharArray();
        int count = 0;
        for (char c : s) {
            if (c == '1') {
                count++;
            }
        }
        return count;
    }

    public int hammingWeight2(int n) {
        int temp = n;
        if (n == 0) {
            return 0;
        }
        int num = 1;
        if (n >>> 16 == 0) {
            num += 16;
            n <<= 16;
        }
        if (n >> 24 == 0) {
            num += 8;
            n <<= 8;
        }
        if (n >> 28 == 0) {
            num += 4;
            n <<= 4;
        }
        if (n >> 30 == 0) {
            num += 2;
            n <<= 2;
        }
        num -= n >>> 31;
        int count = 0;
        for (int i = 0; i < 32 - num; i++) {
            boolean res = ((temp >> i) & 1) == 1;
            if (res) {
                count++;
            }
        }
        return count;

    }

    @Test
    public void test() {
        System.out.println(hammingWeight2(0b11111111111111111111111111111111));
    }
}
