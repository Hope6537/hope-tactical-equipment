package org.hope6537.datastruct.graph;

/**
 * Created by Hope6537 on 2015/3/3.
 */
public interface GraphPath {

    boolean hasPathTo(int vetrex);

    Iterable<Integer> pathTo(int v);

}
