package org.hope6537.note.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/30.
 */
public class PascalsTriangle {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> newList = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    newList.add(1);
                } else {
                    if (i > 1) {
                        List<Integer> list = res.get(i - 1);
                        newList.add(list.get(j - 1) + list.get(j));
                    }
                }
            }
            res.add(newList);
        }
        return res;
    }

    @Test
    public void test() {
        generate(5).forEach(list -> System.out.println(list.toString()));
    }
}
