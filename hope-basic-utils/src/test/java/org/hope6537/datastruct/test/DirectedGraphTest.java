package org.hope6537.datastruct.test;

import org.hope6537.datastruct.graph.DirectedGraph;
import org.hope6537.datastruct.graph.SymbolGraph;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class DirectedGraphTest {

    @Test
    public void testInitGraph() throws IOException {
        InputStream in = SymbolGraph.class.getResourceAsStream("data/tinyDirectedGraph.txt");
        Scanner scanner = new Scanner(in);
        DirectedGraph directedGraph = new DirectedGraph(scanner);
        Iterable<Integer> res = directedGraph.adj(3);
        List<Integer> resList = new ArrayList<Integer>();
        res.forEach(resList::add);
        assertTrue(resList.contains(5) && resList.contains(2));
    }

    @Test
    public void testDirectedReachable() throws IOException {
        InputStream in = SymbolGraph.class.getResourceAsStream("data/tinyDirectedGraph.txt");
        Scanner scanner = new Scanner(in);
        DirectedGraph directedGraph = new DirectedGraph(scanner);
        Set<Integer> input = new HashSet<>();
        input.add(1);
        input.add(2);
        input.add(6);
        directedGraph.thisGraphDirectedTo(input).forEach(System.out::println);
    }

}
