package org.hope6537.note.thinking_in_java.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class five {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        Iterator<String> it = list.iterator();
        for (int i = 0; i < 6; i++) {
            it.next();
            it.remove();
        }
        ListIterator<String> liit = list.listIterator();
        liit.next();
        System.out.println(list);

    }
}
