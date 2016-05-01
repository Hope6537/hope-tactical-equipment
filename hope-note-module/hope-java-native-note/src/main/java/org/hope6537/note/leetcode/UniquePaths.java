package org.hope6537.note.leetcode;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/4/5.
 */
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] arr = new int[m][n];
        for (int i = 0; i < n; i++) {
            arr[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            arr[i][0] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                arr[i][j] = arr[i - 1][j] + arr[i][j - 1];
            }
        }
        return arr[m - 1][n - 1];
    }

    @Test
    public void test() {
        System.out.println(uniquePaths(2, 1));
    }
}
