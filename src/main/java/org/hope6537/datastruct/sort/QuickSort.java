package org.hope6537.datastruct.sort;

/**
 * @Describe 快速排序
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-6下午04:00:12
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class QuickSort {

	/**
	 * @Descirbe 选择枢纽元
	 * @Author Hope6537(赵鹏)
	 * @Params @param <AnyType>
	 * @Params @param a
	 * @Params @param left 左侧的索引游标
	 * @Params @param right 右侧的索引游标
	 * @Params @return
	 * @SignDate 2014-4-6下午04:04:12
	 * @Version 0.9
	 * @param <AnyType>
	 * @param a
	 * @param left
	 * @param right
	 * @return
	 */
	private static <AnyType extends Comparable<? super AnyType>> AnyType median3(
			AnyType[] a, int left, int right) {
		// 找出中值点
		int center = (left + right) / 2;
		// 如果中心小于左定点 则互换
		if (a[center].compareTo(a[left]) < 0) {
			swapReferences(a, left, center);
		}
		// 如果右侧小于左侧 则互换
		if (a[right].compareTo(a[left]) < 0) {
			swapReferences(a, left, right);
		}
		// 如果右侧小于中心 则互换
		if (a[right].compareTo(a[center]) < 0) {
			swapReferences(a, center, right);
		}
		// 这样最后从小到大则是 left center right进行排列
		/*
		 * 将中心的数据 即枢纽元和右侧-1位置的游标进行交换 因为left center right是符合排序序列的
		 * 所以排序的主体是从left+1到right-2的
		 */

		swapReferences(a, center, right - 1);

		return a[right - 1];
	}

	private static final int CUTOFF = 3;

	private static <AnyType extends Comparable<? super AnyType>> void quickSort(
			AnyType[] a, int left, int right) {
		if (left + CUTOFF <= right) {
			// 如果左游标到右游标有三个元素
			// 那么说明枢纽元可用
			AnyType pivot = median3(a, left, right);
			//将从左右边界开始进行遍历
			int i = left;
			int j = right - 1; 
			for (;;) {
				//进入循环 
				while (a[++i].compareTo(pivot) < 0) {
					//如果从左开始遇到的遍历的数比枢纽元的小 则接着遍历 直到遇到大于枢纽元的停止
				}
				while (a[--j].compareTo(pivot) > 0) {
					//如果从右开始语调的遍历的数比枢纽元的大 则接着遍历 直到遇到小于枢纽元的停止
				}
				if (i < j) {
					//如果i j的边界合法 则互换
					swapReferences(a, i, j);
				} else {
					//如果边界不合法 则说明该数组已经排序完成 跳出
					break;
				}
			}
			//将枢纽元换回来
			swapReferences(a, i, right - 1);
			//同时再将两侧的S1 S2数据群进行分割排序 
			quickSort(a, left, i - 1);
			quickSort(a, i, right);

		} else {
			//最后分割成仅有3个元素时 那么插入排序走你,排好的同时递归返回
			insertionSort(a, left, right);
		}
	}

	/**
	 * @Descirbe 带边界的插入排序
	 * @Author Hope6537(赵鹏)
	 * @Params @param <AnyType>
	 * @Params @param a
	 * @Params @param left
	 * @Params @param right
	 * @SignDate 2014-4-6下午04:18:28
	 * @Version 0.9
	 * @param <AnyType>
	 * @param a
	 * @param left
	 * @param right
	 */
	private static <AnyType extends Comparable<? super AnyType>> void insertionSort(
			AnyType[] a, int left, int right) {
		for (int p = left + 1; p <= right; p++) {
			AnyType tmp = a[p];
			int j;

			for (j = p; j > left && tmp.compareTo(a[j - 1]) < 0; j--)
				a[j] = a[j - 1];
			a[j] = tmp;
		}
	}

	/**
	 * @Descirbe 交换元素
	 * @Author Hope6537(赵鹏)
	 * @Params @param <AnyType>
	 * @Params @param a
	 * @Params @param index1
	 * @Params @param index2
	 * @SignDate 2014-4-6下午01:42:56
	 * @Version 0.9
	 * @param <AnyType>
	 * @param a
	 * @param index1
	 * @param index2
	 */
	public static <AnyType> void swapReferences(AnyType[] a, int index1,
			int index2) {
		AnyType tmp = a[index1];
		a[index1] = a[index2];
		a[index2] = tmp;
	}
	
	public static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType [] a){
		quickSort(a, 0, a.length-1);
	}

}
