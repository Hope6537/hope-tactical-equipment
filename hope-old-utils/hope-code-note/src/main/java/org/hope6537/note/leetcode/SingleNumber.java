package org.hope6537.note.leetcode;

import org.junit.Test;

public class SingleNumber {

    public static int singleNumber(int[] A) {
        int res = A[0];
        if (A.length == 1) {
            return res;
        }
        for (int i = 1; i < A.length; i++) {
            res = res ^ A[i];
        }
        return res;
    }

    @Test
    public void testSolution() {
        System.out.println(singleNumber(new int[]{1, 2, 3, 3, 2}));
    }
}