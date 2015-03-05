package org.hope6537.datastruct.graph;

/**
 * Created by Hope6537 on 2015/3/4.
 */
public class DepthFirstConnectedCount implements ConnectedCount {

    protected boolean[] marked;
    protected int count;
    //所在连通分量的标示符
    private int[] id;

    public DepthFirstConnectedCount(BasicGraph graph) {
        marked = new boolean[graph.getVetrex()];
        id = new int[graph.getVetrex()];
        for (int start = 0; start < graph.getVetrex(); start++) {
            if (!marked(start)) {
                search(graph, start);
                count++;
            }
        }
    }

    public void search(BasicGraph graph, int vetrex) {
        marked[vetrex] = true;
        id[vetrex] = count;
        count++;
        for (int w : graph.adj(vetrex)) {
            if (!marked(w)) {
                search(graph, w);
            }
        }
    }

    public boolean marked(int vetrex) {
        return marked[vetrex];
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public boolean connectied(int v, int w) {
        return id[v] == id[w];
    }

    @Override
    public int id(int vetrex) {
        return id[vetrex];
    }
}
