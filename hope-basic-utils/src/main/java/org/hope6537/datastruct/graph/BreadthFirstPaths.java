package org.hope6537.datastruct.graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Hope6537 on 2015/3/4.
 */
public class BreadthFirstPaths implements GraphPath, GraphSearch {

    protected boolean[] marked;

    protected int[] edgeTo;

    protected final int start;

    public BreadthFirstPaths(BasicGraph graph, int start) {
        marked = new boolean[graph.getVetrex()];
        edgeTo = new int[graph.getVetrex()];
        this.start = start;
        search(graph, start);
    }

    @Override
    public void search(BasicGraph graph, int vetrex) {
        Queue<Integer> quene = new LinkedList<>();
        marked[vetrex] = true;
        quene.add(vetrex);
        while (!quene.isEmpty()) {
            int temp_v = quene.poll();
            for (int w : graph.adj(temp_v)) {
                if (!marked[w]) {
                    edgeTo[w] = temp_v;
                    marked[w] = true;
                    quene.add(w);
                }
            }
        }
    }

    @Override
    public boolean marked(int vetrex) {
        return marked[vetrex];
    }

    @Override
    public boolean hasPathTo(int vetrex) {
        return marked[vetrex];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        for (int i = v; i != start; i = edgeTo[i]) {
            path.push(i);
        }
        path.push(start);
        return path;
    }
}
