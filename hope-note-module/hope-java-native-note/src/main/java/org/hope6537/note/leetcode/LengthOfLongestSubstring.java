package org.hope6537.note.leetcode;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LengthOfLongestSubstring {

    public static int lengthOfLongestSubstring(String s) {
        char[] arr = s.toCharArray();
        Set<String> set = new HashSet<>();
        int num = 0;
        for (int i = 0; i < arr.length; i++) {
            boolean bol = set.add(String.valueOf(arr[i]));
            if (!bol) {
                set.clear();
                i = s.lastIndexOf(arr[i], i - 1);
                System.out.println(i);
            }
            if (num < set.size()) {
                num = set.size();
            }
        }
        return num;
    }


    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("1233345"));
    }

}