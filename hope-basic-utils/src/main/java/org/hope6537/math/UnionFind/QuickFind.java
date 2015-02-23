package org.hope6537.math.unionfind;

/**
 * Created by Hope6537 on 2015/2/23.
 */
public class QuickFind extends AbstractUnionFind {

    public QuickFind(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q) {
        //将p和q归到相同的分量中
        int pId = find(p);
        int qId = find(q);
        //如果已经在相同的分量中，那么不需要任何行动
        if (pId == qId) {
            return;
        }
        //将p的分量重命名为q的名称
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pId) {
                id[i] = qId;
            }
        }
        count--;
    }
}
