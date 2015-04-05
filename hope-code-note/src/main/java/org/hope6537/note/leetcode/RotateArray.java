package org.hope6537.note.leetcode;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class RotateArray {

    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reserve(nums, 0, n - k);
        reserve(nums, n - k, n);
        reserve(nums, 0, n);
    }

    public static void reserve(int[] nums, int start, int end) {
        for (int i = start; i < (start + end) / 2; i++) {
            nums[i] ^= nums[start + end - i - 1];
            nums[start + end - i - 1] ^= nums[i];
            nums[i] ^= nums[start + end - i - 1];
        }
    }

    public static void swap(int i, int k) {
        i ^= k;
        k ^= i;
        i ^= k;
    }


    @Test
    public void test() {
        int i = 1;
        int k = 3;
        i ^= k;
        k ^= i;
        i ^= k;
        System.out.println(i + "->" + k);
    }

}
