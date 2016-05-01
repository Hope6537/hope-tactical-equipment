package org.hope6537.datastruct.heap;


/**
 * @param <AnyType>
 * @version 0.9
 * @Describe 二分堆
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-5下午01:12:45
 * @company Changchun University&SHXT
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;

    private AnyType[] array;

    /**
     * @param
     * @Describe 默认为空的构造函数
     * @Author Hope6537(赵鹏)
     */
    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * @param @param capacity
     * @Describe 带大小的构造函数 数据为空
     * @Author Hope6537(赵鹏)
     */
    @SuppressWarnings("unchecked")
    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }


    /**
     * @param @param x
     * @Describe 带有原有数组数据的x的构造方法
     * @Author Hope6537(赵鹏)
     */
    @SuppressWarnings("unchecked")
    public BinaryHeap(AnyType[] x) {
        currentSize += x.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
        //声明数组和长度
        //因为该数组是从1开始的 1作为整个堆的根节点
        int i = 1;
        for (AnyType item : x) {
            array[i++] = item;
        }
        buildHeap();
    }

    /**
     * @param x
     * @Descirbe 将元素x插入进堆中 上滤操作
     * @Author Hope6537(赵鹏)
     * @Params @param x
     * @SignDate 2014-4-5下午01:21:29
     * @Version 0.9
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1); // 如果数组已满 那么重构
        }
        int hole = ++currentSize;//同时将currentSize+1来确保数目，同时将hole设置为尾索引，即当前元素数量
        for (; hole > 1 && x.compareTo(array[hole / 2]) < 0; hole = hole / 2) {
            //将x和array[hole/2]进行对比 即把x和每个位于他上面的父节点进行Compare比较
            array[hole] = array[hole / 2];
            //如果x小于父节点，则上推
        }
        //何时循环跳出 则说明找到位置 放置play~ 这俗称上滤
        array[hole] = x;
    }

    /**
     * @param newSize
     * @Descirbe 将二项堆重构
     * @Author Hope6537(赵鹏)
     * @Params @param newSize
     * @SignDate 2014-4-5下午01:16:26
     * @Version 0.9
     */
    @SuppressWarnings("unchecked")
    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * @param hole
     * @Descirbe 下滤
     * @Author Hope6537(赵鹏)
     * @Params @param hole
     * @SignDate 2014-4-5下午03:27:46
     * @Version 0.9
     */
    private void percolateDown(int hole) {
        int child; // 声明child 即左儿子节点的索引
        AnyType tmp = array[hole]; //将当前hole节点的数据保存下来 即原始的最last元素的值

        for (; hole * 2 <= currentSize; hole = child) {//如果hole的子节点存在
            child = hole * 2;//则进入循环 同时给child赋值
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                //如果child有右节点同时右节点小于左节点
                child++;
                //那么索引值变化 变成右儿子节点
            }
            if (array[child].compareTo(tmp) < 0) {
                //如果儿子节点小于父节点 将父节点换下来 儿子节点作为新的根
                array[hole] = array[child];
            } else {
                //否则相同或者父亲小于儿子的话跳出
                break;
            }
        }
        array[hole] = tmp;
    }

    /**
     * @return
     * @Descirbe 当前二项堆是否为空
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午03:37:58
     * @Version 0.9
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * @return
     * @Descirbe 将堆内最小值取出 同时重新排列整个堆序
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午03:30:50
     * @Version 0.9
     */
    public AnyType deleteMin() {
        if (isEmpty()) {
            //如果为空 直接返回null
            return null;
        }
        //否则 找到当前树根的索引并保存
        AnyType minItem = findMin();
        //将array的最后节点放在根上
        array[1] = array[currentSize--];
        //然后进行下滤操作 实际上就是已经取出了根 将两个子树进行合并
        percolateDown(1);
        //返回最小节点
        return minItem;
    }

    /**
     * @return
     * @Descirbe 返回当前优先队列（堆）的树根 或者说是首项
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午03:30:30
     * @Version 0.9
     */
    private AnyType findMin() {
        if (isEmpty()) {
            return null;
        }
        return array[1];
    }

    /**
     * @Descirbe 对于每个有子节点的节点 进行堆序排序
     * @Author Hope6537(赵鹏)
     * @Params
     * @SignDate 2014-4-5下午04:00:35
     * @Version 0.9
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

}
