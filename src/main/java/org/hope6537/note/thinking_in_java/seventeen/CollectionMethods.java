package org.hope6537.note.thinking_in_java.seventeen;

import java.util.ArrayList;

public class CollectionMethods {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>(), subList = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            if (i < 5) {
                subList.add(i);
            }
        }
        System.out.println(list.removeAll(subList));
        System.out.println(list);

    }
}
