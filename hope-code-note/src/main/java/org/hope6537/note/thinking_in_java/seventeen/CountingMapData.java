package org.hope6537.note.thinking_in_java.seventeen;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CountingMapData extends AbstractMap<Integer, String> {

    private static String[] chars = "A B C D E F G H J K L M N O P Q R S T U V W X Y Z"
            .split(" ");
    private int size;

    public CountingMapData() {
    }

    public CountingMapData(int size) {
        super();
        this.size = size < 0 ? 0 : size;
    }

    public static void main(String[] args) {
        System.out.println(new CountingMapData(30));
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<Integer, String>> entrySet() {
        Set<Map.Entry<Integer, String>> entries = new LinkedHashSet();
        for (int i = 0; i < size; i++) {
            entries.add(new Entry(i));
        }
        return entries;
    }

    private static class Entry implements Map.Entry<Integer, String> {
        int index;

        public Entry(int index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object obj) {
            return Integer.valueOf(index).equals(obj);
        }

        @Override
        public Integer getKey() {
            return index;
        }

        @Override
        public String getValue() {
            return chars[index % chars.length]
                    + Integer.toString(index / chars.length);
        }

        @Override
        public String setValue(String value) {
            System.out.println("No Set");
            return null;
        }

        @Override
        public int hashCode() {
            return Integer.valueOf(index).hashCode();
        }
    }

}
