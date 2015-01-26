package org.hope6537.datastruct.list;

import java.util.LinkedList;

public class ReserveList {

    LinkedList<Integer> l = new LinkedList<Integer>();

    static int[] a1 = {1, 2, 3, 4, 5};

    public static void reserve() {

        for (int i = 1; i < a1.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = a1[j];
                a1[j] = a1[i];
                a1[i] = temp;
            }
        }
    }

    public static void main(String[] args) {

        int[] a1 = {1, 2, 3, 4, 5};

        for (int i = 1; i < a1.length; i++) {
            for (int j = 0; j < i; j++) {
                int temp = a1[j];
                a1[j] = a1[i];
                a1[i] = temp;
            }
        }

        for (int x : a1) {
            System.out.print(" " + x);
        }
    }


}
