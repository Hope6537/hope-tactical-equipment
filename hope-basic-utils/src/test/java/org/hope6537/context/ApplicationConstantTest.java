package org.hope6537.context;

import org.hope6537.date.TimeDemo;
import org.junit.Test;

import static org.hope6537.context.ApplicationConstant.*;

/**
 * Created by Hope6537 on 2015/3/28.
 */
public class ApplicationConstantTest {

    public static final int SORT_QUICK = 0;
    public static final int SORT_SELECT = 1;
    public static final int SORT_INSERT = 2;
    public static final int SORT_SHELL = 3;
    public static final int SORT_MERGE = 4;
    public static final int SORT_HEAP = 5;

    public static Double times(int arg, Comparable[] a) {
        TimeDemo timeDemo = new TimeDemo();
        switch (arg) {
            case SORT_INSERT:
                insertSort(a);
                break;
            case SORT_SELECT:
                selectionSort(a);
                break;
            case SORT_QUICK:
                quick3Sort(a);
                break;
            case SORT_MERGE:
                mergeSort(a);
                break;
            case SORT_SHELL:
                shellSort(a);
                break;

        }
        Double res = timeDemo.end();
        timeDemo.clear();
        return res;
    }

    public static double timeRandomInput(int arg, int n, int t) {
        double total = 0.0;
        Double[] a = new Double[n];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < n; j++) {
                a[j] = Math.random();
            }
            total += times(arg, a);
        }
        return total;
    }

    @Test
    public void testSort() {
        //double time1 = timeRandomInput(SORT_INSERT, 1000, 2);
        //double time2 = timeRandomInput(SORT_SELECT, 1000, 2);
        double time3 = timeRandomInput(SORT_QUICK, 10000, 200);
        double time4 = timeRandomInput(SORT_MERGE, 10000, 200);
        double time5 = timeRandomInput(SORT_SHELL, 10000, 200);
        //System.out.println("SORT_INSERT:" + time1);
        //System.out.println("SORT_SELECT:" + time2);
        System.out.println("SORT_QUICK:" + time3);
        System.out.println("SORT_MERGE:" + time4);
        System.out.println("SORT_SHELL:" + time5);
    }


}
