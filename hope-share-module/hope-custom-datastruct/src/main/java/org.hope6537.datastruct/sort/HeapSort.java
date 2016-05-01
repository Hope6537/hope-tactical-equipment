package org.hope6537.datastruct.sort;

public class HeapSort {

    /**
     * @param x
     * @return
     * @Descirbe 获取堆中的左儿子索引的方法
     * @Author Hope6537(赵鹏)
     * @Params @param x
     * @Params @return
     * @SignDate 2014-4-6下午01:18:12
     * @Version 0.9
     */
    private static int leftChild(int x) {
        /*
         * 这里堆不同的是  堆中元素的左儿子索引值是 2 * i
		 * 但是堆的根部元素是从索引1开始的
		 * 而在堆排序中 索引根部元素是从索引0开始的 所以要+1
		 */

        return 2 * x + 1;
    }

    /**
     * @param <AnyType>
     * @param a
     * @param i
     * @param n
     * @Descirbe 堆排序 建立max堆
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @Params @param i 下标
     * @Params @param n 数据个数
     * @SignDate 2014-4-6下午02:01:14
     * @Version 0.9
     */
    private static <AnyType extends Comparable<? super AnyType>> void percDown(AnyType[] a, int i, int n) {
        int child = 0;  //子节点的下表
        AnyType tmp; //父节点的数据
        for (tmp = a[i]; leftChild(i) < n; i = child) {
            /*System.out.println("a[i]="+a[i]);*/
            //从父节点开始循环 一直到最后一个具有子节点的节点为止
            child = leftChild(i);
            //将child赋值 是i的左儿子
            if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0) {
                //如果 左儿子有兄弟 即右儿子 同时左儿子小于右儿子
                child++;
                //则将索引移到右儿子身上
            }
            if (tmp.compareTo(a[child]) < 0) {
                //其中最大的子节点如果大于根
                /*System.out.println("a[child]="+a[child]);*/
                a[i] = a[child];
                //那么父子互换  形成递增序列
            } else {
                break;
            }
        }
        /*
         * 循环完成 将取出来的节点元素值放在数组的最后 即删除掉的堆存储空间 来放置
		 */
        a[i] = tmp;
    }


    /**
     * @param <AnyType>
     * @param a
     * @param index1
     * @param index2
     * @Descirbe 交换元素
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @Params @param index1
     * @Params @param index2
     * @SignDate 2014-4-6下午01:42:56
     * @Version 0.9
     */
    public static <AnyType> void swapReferences(AnyType[] a, int index1, int index2) {
        AnyType tmp = a[index1];
        a[index1] = a[index2];
        a[index2] = tmp;
    }

    /**
     * @param <AnyType>
     * @param a
     * @Descirbe 堆排序外部接口 主体
     * @Author Hope6537(赵鹏)
     * @Params @param <AnyType>
     * @Params @param a
     * @SignDate 2014-4-6下午01:59:26
     * @Version 0.9
     */
    public static <AnyType extends Comparable<? super AnyType>> void heapSort(AnyType[] a) {
        for (int i = a.length / 2; i >= 0; i--) {
            percDown(a, i, a.length); //建立max堆
        }
        for (int i = a.length - 1; i > 0; i--) {
            swapReferences(a, 0, i); //将元素deleteMax掉 将max元素放在i处 即最后方
            percDown(a, 0, i);//然后再进行堆序建立 找到另外的max
        }
    }

}
