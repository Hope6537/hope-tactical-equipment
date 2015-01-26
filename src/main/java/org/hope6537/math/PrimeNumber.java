package org.hope6537.math;

import java.util.Scanner;

public class PrimeNumber {

	static Scanner s = new Scanner(System.in);

	static boolean[] is;
	static int[] prm;
	static int N = 1000000, M = 1000000;

	static int getPrm(int n) {
		int i, j, k = 0;
		int s, e = (int) (Math.sqrt(0.0 + n) + 1);
		is = new boolean[N];
		for (int l = 1; l < n; l++) {
			is[l] = true;
		}
		prm = new int[M];
		prm[k++] = 2;
		is[0] = is[1] = false;
		for (i = 4; i < n; i += 2) {
			is[i] = false;
		}
		for (i = 3; i < e; i += 2) {
			prm[k++] = i;
			for (s = i * 2, j = i * i; j < n; j += s) {
				is[j] = false;
			}
		}
		for (; i < n; i += 2) {
			if (is[i]) {
				prm[k++] = i;
			}
		}
		return k;
	}

	public static void main(String[] args) {
		getPrm(1000000);
		while (s.hasNext()) {
			int T = s.nextInt();
			int count = 0;
			while (T-- != 0) {

				if (is[s.nextInt()]) {
					count++;
				}

			}
			System.out.println(count);
		}
	}

}
