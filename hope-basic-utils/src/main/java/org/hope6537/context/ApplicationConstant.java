package org.hope6537.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * Created by Zhaopeng-Rabook on 15-1-9.
 */
public class ApplicationConstant {

    public static final int EFFECTIVE_LINE_ONE = 1;
    public static final int EFFECTIVE_LINE_ZERO = 0;
    public static final boolean TURE = true;
    public static final boolean FALSE = false;
    public static final String YES = "是";
    public static final String NO = "否";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public static final String REFRESH = "refreshing";
    public static final String SUCCESS_CHN = "操作成功";
    public static final String FAIL_CHN = "操作失败";
    public static final String ERROR_CHN = "发生错误";
    public static final String REFRESH_CHN = "正在刷新";
    public static final String ROLE_FOUNDER = "创建者";
    public static final String ROLE_READER = "只读";
    public static final String ROLE_WRITER = "读写";
    public static final String STATUS_JUDGE = "待审核";
    public static final String STATUS_NORMAL = "正常";
    public static final String STATUS_DIE = "不可用";

    public static final java.util.regex.Pattern DELIMITER = java.util.regex.Pattern.compile("[\t,]");

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void exchange(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object obj) {
        boolean res = obj != null;
        if (obj instanceof String) {
            return !((String) obj).isEmpty();
        }
        if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        }
        return res;
    }

    public static boolean notNull(Object... objc) {
        boolean res = objc != null;
        if (!res) {
            return false;
        }
        for (Object o : objc) {
            if (o instanceof String) {
                res = res && !((String) o).isEmpty();
            }
            if (o instanceof Collection) {
                res = res && !((Collection) o).isEmpty();
            }
            res = res && o != null;
        }
        return res;
    }

    public static boolean isNull(Object obj) {
        return !notNull(obj);
    }

    public static boolean isNull(Object... objc) {
        return !notNull(objc);
    }


    public static void quick3Sort(Comparable[] a) {
        quick3Sort(a, 0, a.length - 1);
    }

    private static void quick3Sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exchange(a, lt++, i++);
            } else if (cmp > 0) {
                exchange(a, i, gt--);
            } else {
                i++;
            }
        }//现在找到了枢纽元
        //a[lo..lt-1] < v = a[lt...gt] < a[gt+1..hi];
        quick3Sort(a, lo, lt - 1);
        quick3Sort(a, gt + 1, hi);
    }

    public static void selectionSort(Comparable[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int min = i;
            for (int j = min + 1; j < n; j++) {
                if (less(a[j], a[min])) {
                    min = j;
                }
            }
            exchange(a, i, min);
        }
    }

    public static void insertSort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exchange(a, j, j - 1);
            }
        }
    }

    public static void shellSort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exchange(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    public static void mergeSort(Comparable[] a) {
        MergeSort.sort(a);
    }

    private static class MergeSort {

        private static Comparable[] aux;

        public static void setAux(Comparable[] aux) {
            MergeSort.aux = aux;
        }

        public static void sort(Comparable[] a) {
            MergeSort.setAux(new Comparable[a.length]);
            MergeSort.sort(a, 0, a.length - 1);
        }

        private static void sort(Comparable[] a, int lo, int hi) {
            if (hi <= lo) {
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sort(a, lo, mid);
            sort(a, mid + 1, hi);
            merge(a, lo, mid, hi);
        }

        private static void merge(Comparable[] a, int lo, int mid, int hi) {
            int i = lo, j = mid + 1;
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }

            for (int k = lo; k <= hi; k++) {
                //左半边用尽，取右半边元素
                if (i > mid) {
                    a[k] = aux[j++];
                }
                //右半边用尽，取左半边元素
                else if (j > hi) {
                    a[k] = aux[i++];
                }
                //右半边当前元素小于左半边当前元素，取右半边元素
                else if (less(aux[j], aux[i])) {
                    a[k] = aux[j++];
                }
                //右半边当前元素大于左半边当前元素，取左半边元素
                else {
                    a[k] = aux[i++];
                }
            }
        }
    }

}
