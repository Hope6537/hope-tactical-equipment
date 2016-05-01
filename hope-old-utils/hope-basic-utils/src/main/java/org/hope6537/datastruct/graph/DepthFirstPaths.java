package org.hope6537.datastruct.graph;

import java.util.Stack;

/**
 * 基于深度优先搜素计算路径
 */
public class DepthFirstPaths extends DepthFirstSearch implements GraphPath {


    /**
     * 从起点到一个顶点的已知路径上的最后一个顶点
     */
    protected int[] edgeTo;

    /**
     * 搜索起点
     */
    protected int start;

    public DepthFirstPaths(BasicGraph graph, int start) {
        super();
        marked = new boolean[graph.getVetrex()];
        edgeTo = new int[graph.getVetrex()];
        this.start = start;
        search(graph, start);
    }

    @Override
    public void search(BasicGraph graph, int vetrex) {
        marked[vetrex] = true;
        count++;
        for (int w : graph.adj(vetrex)) {
            if (!marked(w)) {
                //标记该路径终点
                edgeTo[w] = vetrex;
                search(graph, w);
            }
        }
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
