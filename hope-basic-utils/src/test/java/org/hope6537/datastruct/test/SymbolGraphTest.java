package org.hope6537.datastruct.test;

import org.apache.commons.io.IOUtils;
import org.hope6537.datastruct.graph.BasicGraph;
import org.hope6537.datastruct.graph.SymbolGraph;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Hope6537 on 2015/3/4.
 */
public class SymbolGraphTest {

    @Test
    public void testInit() throws IOException {
        InputStream in = SymbolGraph.class.getResourceAsStream("data/routes.txt");
        String stream = IOUtils.toString(in);
        String split = " ";
        SymbolGraph symbolGraph = new SymbolGraph(stream, split);
        BasicGraph basicGraph = symbolGraph.getGraph();
        Scanner s = new Scanner("JFK\r\nLAX");
        while (s.hasNextLine()) {
            String source = s.nextLine();
            System.out.println(source);
            for (int w : basicGraph.adj(symbolGraph.index(source))) {
                System.out.println("\t" + symbolGraph.name(w));
            }
        }
    }

    @Test
    public void testScanner() {
        Scanner scanner = new Scanner("Ssd sdsd sdsd sds ds \r\n dqwdqwd dqwdqwd dqw dqw");
        while (scanner.hasNextLine()) {
            System.out.println(Arrays.toString(scanner.nextLine().split(" ")));
        }
        scanner.reset();
        while (scanner.hasNextLine()) {
            System.out.println(Arrays.toString(scanner.nextLine().split(" ")));
        }
    }


}
