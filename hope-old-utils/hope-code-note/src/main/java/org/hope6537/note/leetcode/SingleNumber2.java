package org.hope6537.note.leetcode;

import org.junit.Test;

public class SingleNumber2 {

    /*
     * ����1������һ������Ϊsizeof(int) ������count[sizeof(int)]��count[i] ��ʾ����iλ
     * ���ֵ�1�Ĵ��������count[i] ��3 ��������������ԣ�����ͰѸ�λȡ������ɴ𰸡�
     */
    public int singleNumber(int[] A) {
        int[] count = new int[32];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < 32; j++) {
                count[j] += (A[i] >> j) & 1;
                count[j] %= 3;
            }
        }
        int result = 0;
        for (int j = 0; j < 32; j++) {
            result += (count[j] << j);
        }
        return result;
    }

    /*
     * �ö�����ģ������������
     */
    public int singleNumber2(int[] A) {
        int one = 0, two = 0, three = 0;
        // ��������Ϊ3,3,3
        for (int a : A) {
            // 00 & 11 = 00 00|00 = 00 two = 00
            // two = 11 & 11 = 11 | 00 = 11;
            // two = 00 & 11 = 00 | 11 = 11;
            two = two | (one & a);
            // one = 00 ^ 11 = 11
            // one = 11 ^ 11 = 00
            // one = 00 ^ 11 = 11
            one ^= a;
            // three = ~(11 & 00) = ~00 = 11
            // three = ~(00 & 11) = ~00 = 11
            // three = ~(11&11) = 00
            three = ~(one & two);
            // one = 11 & 11 = 11
            // one = 00 & 11 = 00
            // one = 11 & 00 = 00
            one &= three;
            // two = 00 & 11 = 00
            // two = 11 & 11 = 11
            // two = 11 & 00 = 00
            two &= three;
            // ���ε�����Ϊ0
        }
        return one;
    }

    @Test
    public void testSolution() {
        System.out.println(singleNumber(new int[]{4, 4, 4, 5, 5, 5, 2}));
    }
}
