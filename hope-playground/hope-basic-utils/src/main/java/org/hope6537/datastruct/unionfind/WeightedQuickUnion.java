package org.hope6537.datastruct.unionfind;

/**
 * 分权并查集
 */
public class WeightedQuickUnion extends AbstractUnionFind {
    /**
     * 各个根节点所对应的分量的大小
     */
    private int[] sz;


    public WeightedQuickUnion(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
        sz = new int[n];
        for (int i = 0; i < n; i++) {
            sz[i] = 1;
        }

    }

    @Override
    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    @Override
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) {
            return;
        }
        //将权重小的树连接到大树的根节点
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
    }
}
