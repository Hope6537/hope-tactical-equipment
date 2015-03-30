package org.hope6537.search.test;

import org.hope6537.search.LinkedSearchTable;
import org.hope6537.search.SearchTable;
import org.junit.Test;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Hope6537 on 2015/3/29.
 */
public class SearchTableTest {

    public static final Pattern DELIMITER = Pattern.compile("[\t,]");

    @Test
    public void testLinkedST() {
        SearchTable<String, Integer> table = new LinkedSearchTable<>();
        InputStream in = LinkedSearchTable.class.getResourceAsStream("data/key-value.txt");
        Scanner scanner = new Scanner(in);
        while (scanner.hasNextLine()) {
            String[] tokens = DELIMITER.split(scanner.nextLine());
            table.put(tokens[0], Integer.valueOf(tokens[1]));
        }
        table.getKeys().forEach(key -> System.out.println(key + " -> " + table.get(key)));
        assertEquals(table.get("E"), Integer.valueOf(12));
        assertEquals(table.get("A"), Integer.valueOf(8));
        table.delete("E");
        assertNull(table.get("E"));
        System.out.println("======");
        table.getKeys().forEach(key -> System.out.println(key + " -> " + table.get(key)));

    }

}
