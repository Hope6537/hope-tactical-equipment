package org.hope6537.datastruct.unionfind;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Union-find算法
 */
public abstract class AbstractUnionFind {

    /**
     * 分量id 以触点作为索引
     */
    protected int[] id;

    /**
     * 分量数量
     */
    protected int count;

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public abstract int find(int p);

    public abstract void union(int p, int q);

}

class UnionFindTest {

    static Scanner s;

    public static void main(String[] args) throws Exception {
        InputStreamReader reader = new InputStreamReader(UnionFindTest.class.getResourceAsStream("data/tinyUF.txt"));
        s = new Scanner(new BufferedReader(reader));
        int n = s.nextInt();
        AbstractUnionFind unionFind = new QuickFind(n);
        while (s.hasNext()) {
            int p = s.nextInt();
            int q = s.nextInt();
            if (unionFind.connected(p, q)) {
                continue;
            }
            unionFind.union(p, q);
            System.out.println(p + " -- " + q);
        }
        System.out.println(unionFind.count() + "components");

    }
}
