package org.hope6537.datastruct.graph;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 有向图中的深度优先搜索的顶点排序
 */
public class DepthFirstOrder {

    private boolean[] marked;

    private Queue<Integer> pre;

    private Queue<Integer> post;

    private Stack<Integer> reversePost;

    public DepthFirstOrder(DirectedGraph graph) {
        pre = new LinkedList<>();
        post = new LinkedList<>();
        reversePost = new Stack<>();
        marked = new boolean[graph.getVetrex()];
        for (int i = 0; i < marked.length; i++) {
            if (!marked[i]) {
                search(graph, i);
            }
        }

    }

    private void search(DirectedGraph graph, int vertex) {
        pre.add(vertex);
        marked[vertex] = true;
        for (int w : graph.adj(vertex)) {
            if (!marked[w]) {
                search(graph, w);
            }
        }
        post.add(vertex);
        reversePost.add(vertex);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
