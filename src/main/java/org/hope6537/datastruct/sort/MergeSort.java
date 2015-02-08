package org.hope6537.datastruct.sort;

/**
 * @version 0.9
 * @Describe 归并排序
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-6下午02:23:41
 * @company Changchun University&SHXT
 */
public class MergeSort {

    /**
     * @param <AnyType>
     * @param a         本体数组
     * @param tmpArray  缓存数组
     * @param left      本体数组 有效域的左定点
     * @param right     本体数组有效域的右定点
     * @Descirbe 归并排序驱动方法
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @Params @param tmpArray
     * @Params @param left
     * @Params @param right
     * @SignDate 2014-4-6下午02:23:49
     * @Version 0.9
     */
    private static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a, AnyType[] tmpArray, int left, int right) {
        if (left < right) {
            //首先索引合法 找出中值
            int center = (left + right) / 2;
            //然后递归分割 左侧索引到中间
            mergeSort(a, tmpArray, left, center);
            //递归分割第二步 中间索引到右侧
            mergeSort(a, tmpArray, center + 1, right);
            //6份
            merge(a, tmpArray, left, center + 1, right);
        }
    }

    /**
     * @param <AnyType>
     * @param a
     * @param tmpArray
     * @param leftPos
     * @param rightPos
     * @param rightEnd
     * @Descirbe 归并排序主例程
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @Params @param tmpArray
     * @Params @param leftPos 左游标的位置
     * @Params @param rightPos 右游标的位置
     * @Params @param rightEnd 右游标的极限
     * @SignDate 2014-4-6下午03:52:03
     * @Version 0.9
     */
    private static <AnyType extends Comparable<? super AnyType>> void merge(AnyType[] a, AnyType[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1; //这是左游标的极限 即center-1
        int tmpPos = leftPos;//从0开始
        int numElement = rightEnd - leftPos + 1;//获得的是数据的总个数
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            //如果左右游标分别符合范围并合法
            if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                //那么开始比较 如果左游标比右游标的数据小
                tmpArray[tmpPos++] = a[leftPos++];
                //那么将该左游标数据赋给临时数组的当前位置 ，并将 tmpPos后移
            } else {
                //反之右游标的数据小的话 将右游标的数据赋给临时数组的当前位置 并将两个游标右移
                tmpArray[tmpPos++] = a[rightPos++];
            }
        }
        //如果分开的数组中 哪个的值剩下了 那么直接按照顺序添加
        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = a[leftPos++];
        }
        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = a[rightPos++];
        }
        //最后将临时数组的数据覆盖回a
        for (int i = 0; i < numElement; i++, rightEnd--) {
            a[rightEnd] = tmpArray[rightEnd];
        }
    }

    /**
     * @param <AnyType>
     * @param a
     * @Descirbe 归并排序的主程序
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @SignDate 2014-4-6下午03:59:25
     * @Version 0.9
     */
    @SuppressWarnings("unchecked")
    public static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a) {
        AnyType[] tmpArray = (AnyType[]) new Comparable[a.length];
        mergeSort(a, tmpArray, 0, a.length - 1);

    }
}
