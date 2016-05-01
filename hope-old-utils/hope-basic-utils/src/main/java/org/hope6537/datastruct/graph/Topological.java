package org.hope6537.datastruct.graph;

import org.hope6537.context.ApplicationConstant;

/**
 * Created by Hope6537 on 2015/3/28.
 */
public class Topological {

    private Iterable<Integer> order;

    public Topological(DirectedGraph graph) {
        this(graph, "reverse");
    }

    public Topological(DirectedGraph graph, String type) {
        DirectedCycle cycleFinder = new DirectedCycle(graph);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder search = new DepthFirstOrder(graph);
            switch (type) {
                case "pre":
                    order = search.pre();
                    break;
                case "post":
                    order = search.post();
                    break;
                case "reverse":
                    order = search.reversePost();
                    break;
                default:
                    order = search.reversePost();
            }
        }
    }

    public Iterable<Integer> getOrder() {
        return order;
    }

    public boolean isDAG() {
        return ApplicationConstant.notNull(order);
    }

}

