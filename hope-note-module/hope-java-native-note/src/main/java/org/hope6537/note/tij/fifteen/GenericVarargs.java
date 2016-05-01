package org.hope6537.note.tij.fifteen;

import java.util.ArrayList;
import java.util.List;

public class GenericVarargs {

    public static <T> List<T> makeList(T... args) {
        List<T> result = new ArrayList<T>();
        for (T item : args) {
            result.add(item);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> list = makeList("djasdhwqpdfioqwegdj16437hewgqdhewqjkdhdhjsadhhsdjhfd".split(""));
        System.out.println(list);
    }

}
