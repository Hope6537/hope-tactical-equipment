package org.hope6537.datastruct.sort;

public class Sort {

    /**
     * @param <AnyType>
     * @param a
     * @Descirbe 插入排序
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @SignDate 2014-4-6下午12:45:25
     * @Version 0.9
     */
    public static <AnyType extends Comparable<? super AnyType>> void insertionSort(AnyType[] a) {
        int j = 0;
        for (int p = 1; p < a.length; p++) {
            //从第二个元素开始到尾部
            AnyType tmp = a[p]; //然后获取第p个元素
            for (j = p; j > 0 && tmp.compareTo(a[j - 1]) < 0; j--) {
                //进行循环  j的索引从p开始 到j-1元素大于j元素时  交换
                a[j] = a[j - 1];
            }
            //最后变量交换完毕
            a[j] = tmp;
        }
    }

    /**
     * @param <AnyType>
     * @param a
     * @Descirbe 希尔排序
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @SignDate 2014-4-6下午12:57:15
     * @Version 0.9
     */
    public static <AnyType extends Comparable<? super AnyType>> void shellsort(AnyType[] a) {
        int j = 0; //作为一个临时下标
        //从总长的一半开始 当gap>0时 将变量折半设置 gap作为一个跳跃索引
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            //从之前的折半变量开始到数组末尾
            for (int i = gap; i < a.length; i++) {
                //依次寻找并设置变量 并将其保存
                AnyType tmp = a[i];
                //将i的值赋给j 如果j大于gap索引 同时j索引的元素小于 j-gap （即在本跳跃方式中序列不同） 则互换
                for (j = i; j >= gap && tmp.compareTo(a[j - gap]) < 0; j -= gap) {
                    //元素互换
                    a[j] = a[j - gap];
                }
                //最后变量交换完毕
                a[j] = tmp;
            }
        }
    }

    public static <AnyType extends Comparable<? super AnyType>> void BooSort(AnyType[] a) {
        AnyType temp = null;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < i; j++) {
                if (a[i].compareTo(a[j]) < 0) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }

}
