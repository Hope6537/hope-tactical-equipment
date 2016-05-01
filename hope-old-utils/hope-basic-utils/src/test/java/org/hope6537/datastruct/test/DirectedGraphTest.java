package org.hope6537.datastruct.test;

import org.hope6537.datastruct.graph.DirectedGraph;
import org.hope6537.datastruct.graph.Topological;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class DirectedGraphTest {

    @Test
    public void testInitGraph() throws IOException {
        InputStream in = DirectedGraph.class.getResourceAsStream("data/tinyDG.txt");
        Scanner scanner = new Scanner(in);
        DirectedGraph directedGraph = new DirectedGraph(scanner);
        Iterable<Integer> res = directedGraph.adj(3);
        List<Integer> resList = new ArrayList<Integer>();
        res.forEach(resList::add);
        assertTrue(resList.contains(5) && resList.contains(2));
    }

    @Test
    public void testDirectedReachable() throws IOException {
        InputStream in = DirectedGraph.class.getResourceAsStream("data/tinyDG.txt");
        Scanner scanner = new Scanner(in);
        DirectedGraph directedGraph = new DirectedGraph(scanner);
        Set<Integer> input = new HashSet<>();
        input.add(1);
        input.add(2);
        input.add(6);
        directedGraph.thisGraphDirectedTo(input).forEach(System.out::println);
    }

    @Test
    public void testTopologicalTest() throws IOException {
        InputStream in = DirectedGraph.class.getResourceAsStream("data/tinyDAG.txt");
        Scanner scanner = new Scanner(in);
        DirectedGraph graph = new DirectedGraph(scanner);
        Topological top = new Topological(graph);
        assertTrue(top.isDAG());
        top.getOrder().forEach(System.out::println);

    }

}
