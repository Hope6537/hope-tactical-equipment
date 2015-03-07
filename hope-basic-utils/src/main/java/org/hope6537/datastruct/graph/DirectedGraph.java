package org.hope6537.datastruct.graph;

/**
 * Created by Hope6537 on 2015/3/7.
 */
public class DirectedGraph extends BasicGraph {


    public DirectedGraph(int vetrex) {
        super(vetrex);
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
}
