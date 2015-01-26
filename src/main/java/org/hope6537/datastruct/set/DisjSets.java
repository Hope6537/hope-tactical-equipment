package org.hope6537.datastruct.set;

/**
 * @Describe 不相交集合类
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-7下午08:09:49
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class DisjSets {

	/**
	 * @Describe 这是非显示表示数据的数组 或者说是元素的父集合 下标是元素的索引 而数组的数据是树的高度的相反数
	 */
	private int[] s;

	/**
	 * @Describe 给定元素数量的构造方法
	 * @Author Hope6537(赵鹏)
	 * @param @param numElements
	 */
	public DisjSets(int numElements) {
		s = new int[numElements];
		for (int i = 0; i < s.length; i++) {
			s[i] = -1;
		}
	}

	/**
	 * @Descirbe 灵巧求并法
	 * @Author Hope6537(赵鹏)
	 * @Params @param root1
	 * @Params @param root2
	 * @SignDate 2014-4-7下午08:15:30
	 * @Version 0.9
	 * @param root1
	 *            数据在s上的索引
	 * @param root2
	 *            数据在s上的索引
	 */
	public void union(int root1, int root2) {
		/*
		 * 如果root2的所在位置更深
		 */
		if (s[root2] < s[root1]) {
			// 那么将root1插入进root2的树中 当做子树来存在
			s[root1] = root2;
		} else {
			// 如果两者高度相同或者root1更深
			if (s[root1] == s[root2]) {
				// 如果相同 深度+1 仅有数据相同的情况下
				s[root1]--;

			}
			// 然后将root2放入root1的树下当做子树来存在
			s[root2] = root1;
		}
	}

	/**
	 * @Descirbe 检查x索引的元素在哪个集合里 即它的根节点
	 * @Author Hope6537(赵鹏)
	 * @Params @param x
	 * @Params @return
	 * @SignDate 2014-4-7下午08:27:41
	 * @Version 0.9
	 * @param x
	 * @return
	 */
	public int find(int x) {
		if (s[x] < 0) {
			return x;
		} else {
			return find(s[x]);
		}
	}

	public static void main(String[] args) {
		int NumElements = 128;
		int NumInSameSet = 16;

		DisjSets ds = new DisjSets(NumElements);
		int set1, set2;

		for (int k = 1; k < NumInSameSet; k *= 2) {
			for (int j = 0; j + k < NumElements; j += 2 * k) {
				set1 = ds.find(j);
				set2 = ds.find(j + k);
				ds.union(set1, set2);
			}
		}

		for (int i = 0; i < NumElements; i++) {
			System.out.print(ds.find(i) + "*");
			if (i % NumInSameSet == NumInSameSet - 1)
				System.out.println();
		}
		System.out.println();
	}

}
