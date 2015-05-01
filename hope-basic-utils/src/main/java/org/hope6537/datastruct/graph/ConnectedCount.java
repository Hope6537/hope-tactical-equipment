package org.hope6537.datastruct.graph;

/**
 * 寻找联通分量
 */
public interface ConnectedCount {

    /**
     * w 和 v 联通么？
     */
    boolean connected(int v, int w);

    /**
     * 联通分量数
     */
    int count();

    int id(int vetrex);
}
