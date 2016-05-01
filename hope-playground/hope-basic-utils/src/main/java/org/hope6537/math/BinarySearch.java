package org.hope6537.math;

public class BinarySearch {

    /**
     * @param <T>
     * @param a
     * @param x
     * @return 返回值是该元素在数组中的下标数
     * @Descirbe 折半查找
     * @Author Hope6537(赵鹏)
     * @Params @param <T>
     * @Params @param a 首先必须将A排序
     * @Params @param x
     * @Params @return
     * @SignDate 2014-3-3下午07:00:44
     * @Version 0.9
     */
    public static <T extends Comparable<? super T>> int binarySearch(T[] a, T x) {
        //从第一行开始注释 首先是定义类型 T 继承于 Comparable
        /*
         * 而关于Comparable接口的作用
		 * 实现了Comparable接口的类在一个Collection(集合）里是可以排序的
		 * 而排序的规则是按照你实现的Comparable里的抽象方法compareTo(Object o) 方法来决定的。 
		 */

        int low = 0, high = a.length - 1;
        // 首先定制低位和高位

        while (low <= high) {
            //如果低位和高位逻辑成立
            int mid = (low + high) / 2;
            //算出数组的中心索引
            if (a[mid].compareTo(x) < 0) {
                //如果中心的元素的比较值小于0 即目标在中央的右侧
                low = mid + 1;//则此时低位变成中心点+1，重新进入循环查找
            } else if (a[mid].compareTo(x) > 0) {
                //而如果中心元素的比较值大于一 及目标在中央的左侧
                high = mid - 1;//此时高位变成中心点-1，重新进入循环查找
            } else {
                //正好即输出
                return mid;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5, 6, 7};
        System.out.println(binarySearch(a, 2));
    }
}
