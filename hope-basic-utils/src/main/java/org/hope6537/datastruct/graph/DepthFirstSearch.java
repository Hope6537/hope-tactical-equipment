package org.hope6537.datastruct.graph;

/**
 * 深度优先搜素驱动
 */
public class DepthFirstSearch implements GraphSearch {

    protected boolean[] marked;

    protected int count;

    public DepthFirstSearch(BasicGraph graph, int start) {
        marked = new boolean[graph.getVetrex()];
        search(graph, start);
    }

    public DepthFirstSearch() {
    }

    @Override
    public void search(BasicGraph graph, int vetrex) {
        marked[vetrex] = true;
        count++;
        for (int w : graph.adj(vetrex)) {
            if (!marked(w)) {
                search(graph, w);
            }
        }
    }

    @Override
    public boolean marked(int vetrex) {
        return marked[vetrex];
    }

    @Override
    public int count() {
        return count;
    }
}
