package org.hope6537.datastruct.sort;

import java.util.Random;

/**
 * @Describe 猴子排序 时间复杂度 最坏情况下 无穷 （量子时代最快算法）
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-3-14下午09:42:50
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class BogoSort <AnyType extends Comparable<? super AnyType>> {

	Random random = new Random();

	public void bogoSort(AnyType[] n) {
		while (!inOrder(n)){
			shuffle(n);
		}
	}

	public void shuffle(AnyType[] n) {
		for (int i = 0; i < n.length; i++) {
			int swapPosition = random.nextInt(i + 1);
			AnyType temp = n[i];
			n[i] = n[swapPosition];
			n[swapPosition] = temp;
		}
	}

	public boolean inOrder(AnyType[] n) {
		for (int i = 0; i < n.length - 1; i++) {
			if (n[i].compareTo(n[i + 1])>0){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		Integer[] a = {1,5,8,9,8,10,22,74,66,8,897,4653,74,4321,432,56};
		new BogoSort().bogoSort(a);
		
		}
		
		
}
