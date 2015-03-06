package org.hope6537.datastruct.test;

import org.apache.commons.io.IOUtils;
import org.hope6537.datastruct.graph.BasicGraph;
import org.hope6537.datastruct.graph.SymbolGraph;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

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
        String result = "";
        while (s.hasNextLine()) {
            String source = s.nextLine();
            for (int w : basicGraph.adj(symbolGraph.index(source))) {
                result += symbolGraph.name(w) + " ";
            }
        }
        assertEquals(result, "ORD ATL MCO LAS PHX ");
    }

    @Test
    public void testDegrees() throws IOException {
        InputStream in = SymbolGraph.class.getResourceAsStream("data/routes.txt");
        String stream = IOUtils.toString(in);
        String split = " ";
        SymbolGraph symbolGraph = new SymbolGraph(stream, split);
        symbolGraph.printDegreesOnConsole("JFK","LAS DFW");

    }

}
