package org.hope6537.datastruct.graph;

import java.util.Stack;

/**
 * 检测有向图的环
 */
public class DirectedCycle implements GraphSearch {

    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public DirectedCycle(DirectedGraph graph) {
        onStack = new boolean[graph.getVetrex()];
        edgeTo = new int[graph.getVetrex()];
        marked = new boolean[graph.getVetrex()];
        for (int vetrex = 0; vetrex < graph.getVetrex(); vetrex++) {
            if (!marked[vetrex]) {
                search(graph, vetrex);
            }
        }
    }

    @Override
    public void search(BasicGraph graph, int vetrex) {
        onStack[vetrex] = true;
        marked[vetrex] = true;
        for (int w : graph.adj(vetrex)) {
            if (this.hasCycle()) {
                return;
            } else if (!marked(w)) {
                edgeTo[w] = vetrex;
                search(graph, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                for (int x = vetrex; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(vetrex);
            }
        }
        onStack[vetrex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    @Override
    public boolean marked(int vetrex) {
        return false;
    }
}
