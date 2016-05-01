package org.hope6537.datastruct.graph;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/7.
 */
public class DirectedGraph extends BasicGraph {


    public DirectedGraph(int vetrex) {
        super(vetrex);
    }

    public DirectedGraph(Scanner scanner) {
        super(scanner);
    }

    @Override
    public void addEdge(int v, int w) {
        //添加单向边
        adj[v].add(w);
    }

    /**
     * 返回该有向图的反向图
     */
    public DirectedGraph reverse() {
        DirectedGraph directedGraph = new DirectedGraph(this.vetrex);
        for (int v = 0; v < vetrex; v++) {
            for (int w : adj(v)) {
                directedGraph.addEdge(w, v);
            }
        }
        return directedGraph;
    }

    /**
     * 驱动方法
     * 获取该有向图中的某个顶点或者某些顶点能够到达哪些点
     */
    public Set<Integer> thisGraphDirectedTo(Iterable<Integer> sources) {
        Set<Integer> res = new HashSet<>();
        DirectedDFS dfs = new DirectedDFS(this, sources);
        for (int vetrex = 0; vetrex < this.getVetrex(); vetrex++) {
            if (dfs.marked(vetrex)) {
                res.add(vetrex);
            }
        }
        return res;
    }

    /**
     * 获取该有向图中的某个顶点或者某些顶点能够到达哪些点
     */
    private static class DirectedDFS {
        private boolean[] marked;

        public DirectedDFS(DirectedGraph graph, int start) {
            marked = new boolean[graph.getVetrex()];
            dfs(graph, start);
        }

        public DirectedDFS(DirectedGraph graph, Iterable<Integer> sources) {
            marked = new boolean[graph.getVetrex()];
            for (int s : sources) {
                if (!marked[s]) {
                    dfs(graph, s);
                }
            }
        }

        private void dfs(DirectedGraph graph, int start) {
            marked[start] = true;
            for (int w : graph.adj(start)) {
                if (!marked[w]) {
                    dfs(graph, w);
                }
            }
        }

        public boolean marked(int index) {
            return marked[index];
        }
    }
}
