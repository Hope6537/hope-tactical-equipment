package org.hope6537.datastruct.graph;

import org.hope6537.datastruct.list.Bag;

import java.util.Scanner;

/**
 * 普通无向图数据结构
 * Created by Hope6537 on 2015/3/3.
 */
public class BasicGraph {

    /**
     * 顶点数目
     */
    protected final int vetrex;

    /**
     * 边的数目
     */
    protected int edge;

    /**
     * 邻接表 使用链表的方式
     */
    protected Bag<Integer>[] adj;

    public BasicGraph(int vetrex) {
        this.vetrex = vetrex;
        this.edge = 0;
        adj = (Bag<Integer>[]) new Bag[vetrex];
        for (int i = 0; i < vetrex; i++) {
            adj[i] = new Bag<Integer>();
        }
    }

    /**
     * 从文件中获取图信息
     * 第一个数字为顶点的数目
     * 第二个数字为边的数目
     * 依次的一对数字将是一对相邻的顶点
     */
    public BasicGraph(Scanner scanner) {
        this(scanner.nextInt());
        //得出边的数目
        int e = scanner.nextInt();
        for (int i = 0; i < e; i++) {
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            addEdge(v, w);
        }
    }

    public int getEdge() {
        return edge;
    }

    public int getVetrex() {
        return vetrex;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        edge++;
    }

    /**
     * 返回一个顶点的邻接表
     */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }
}

