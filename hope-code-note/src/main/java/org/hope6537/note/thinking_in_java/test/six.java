package org.hope6537.note.thinking_in_java.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class six {

    public static void main(String[] args) {
        Random random = new Random(47);
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < 10000; i++) {
            Integer r = random.nextInt(20);
            Integer res = map.get(r);
            map.put(r, res == null ? 1 : res + 1);
        }
        System.out.println(map);
    }
}
