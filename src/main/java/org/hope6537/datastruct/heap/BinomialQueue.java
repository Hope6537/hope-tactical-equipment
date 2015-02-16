package org.hope6537.datastruct.heap;

/**
 * @param <AnyType>
 * @version 0.9
 * @Describe 二项队列
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-5下午08:12:18
 * @company Changchun University&SHXT
 */
public class BinomialQueue<AnyType extends Comparable<? super AnyType>> {

    /**
     * @Describe 默认二项队列中树的数量
     */
    private static final int DEFAULT_TREES = 1;
    private int currentSize;
    private Node<AnyType>[] theTrees;

    /**
     * @param
     * @Describe 默认构造方法
     * @Author Hope6537(赵鹏)
     */
    @SuppressWarnings("unchecked")
    public BinomialQueue() {
        theTrees = new Node[DEFAULT_TREES];
        makeEmpty();
    }

    /**
     * @param @param item
     * @Describe 默认构造方法 带树根
     * @Author Hope6537(赵鹏)
     */
    @SuppressWarnings("unchecked")
    public BinomialQueue(AnyType item) {
        currentSize = 1;
        theTrees = new Node[1];
        theTrees[0] = new Node<AnyType>(item, null, null);
    }

    public static void main(String[] args) {
        int numItems = 10000;
        BinomialQueue<Integer> h = new BinomialQueue<Integer>();
        BinomialQueue<Integer> h1 = new BinomialQueue<Integer>();
        int i = 37;

        System.out.println("Starting check.");

        for (i = 37; i != 0; i = (i + 37) % numItems)
            if (i % 2 == 0)
                h1.insert(i);
            else
                h.insert(i);

        h1.merge(h);

        for (int i2 = 0; i2 < h1.currentSize; i2++) {
            System.out.println(h1.deleteMin());
        }

        System.out.println("Check done.");
    }

    /**
     * @return
     * @Descirbe 返回是否为空
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午08:22:49
     * @Version 0.9
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * @Descirbe 清空二项队列中的元素
     * @Author Hope6537(赵鹏)
     * @Params
     * @SignDate 2014-4-5下午08:22:55
     * @Version 0.9
     */
    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < theTrees.length; i++) {
            theTrees[i] = null;
        }
    }

    /**
     * @return
     * @Descirbe 返回树中的树的深度？
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午08:20:37
     * @Version 0.9
     */
    private int capacity() {
        return (1 << theTrees.length) - 1;
    }

    /**
     * @return
     * @Descirbe 找到最小项
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-6上午12:13:26
     * @Version 0.9
     */
    public AnyType findMin() {
        if (isEmpty()) {
            return null;
        }

        return theTrees[findMinIndex()].element;
    }


    /**
     * @return
     * @Descirbe 返回二项队列中最小的树的索引
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午08:19:40
     * @Version 0.9
     */
    private int findMinIndex() {
        int i = 0;
        int minIndex = 0;
        for (i = 0; theTrees[i] == null; i++) {
            ;
        }
        for (minIndex = i; i < theTrees.length; i++) {
            if (theTrees[i] != null
                    && theTrees[i].element
                    .compareTo(theTrees[minIndex].element) < 0) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    /**
     * @param t1
     * @param t2
     * @return
     * @Descirbe 合并两颗同样大小的二项树的例程
     * @Author Hope6537(赵鹏)
     * @Params @param t1
     * @Params @param t2
     * @Params @return
     * @SignDate 2014-4-5下午08:27:20
     * @Version 0.9
     */
    private Node<AnyType> combineTrees(Node<AnyType> t1, Node<AnyType> t2) {
        /*
         * 总而言之 是将较大的树的根下降一层 作为较小的树的左儿子 如果t1比t2要大 则翻转回来
		 */
        if (t1.element.compareTo(t2.element) > 0) {
            return combineTrees(t2, t1);
        }
        // 将较小项的左子书插入到较大项的右子树上
        t2.nextSibling = t1.leftChild;
        // 而同时将较大项（包括原本的t1左子书和t2本身）插入到t1的左侧 作为t1的左儿子存在
        t1.leftChild = t2;
        // 将新生成的根 t1返回
        return t1;
    }

    /**
     * @param rhs
     * @Descirbe 合并两个二项队列的例程
     * @Author Hope6537(赵鹏)
     * @Params @param rhs 参数是作为合并项的另一个二项队列
     * @SignDate 2014-4-5下午08:51:28
     * @Version 0.9
     */
    public void merge(BinomialQueue<AnyType> rhs) {
        // 如果两个队列相等 直接返回
        if (this == rhs) {
            return;
        }
        // 否则如果不相等
        // 那么将本体的size和合并的相加
        currentSize += rhs.currentSize;
        if (currentSize > capacity()) {
            // 如果本体的树容器容纳不下 那么重新构成新的二项队列
            int maxLength = Math.max(theTrees.length, rhs.theTrees.length) + 1;
            // 创建新的树存储序列
            expandTheTrees(maxLength);
        }
        // 这是上一次参加合并所遗留下来的 秩为i+1的树
        Node<AnyType> carry = null;
		/*
		 * 秩从0开始 而j即节点的数量从1开始 一旦j的数量超过了应有数量 跳出 同时秩i的增长是++ 而j即树的个数增长是j*2的方式增加
		 * 关于j的研究方式 是按照B0 B1 B2 .. Bi的节点的数量产生的 由于二项队列的特殊性 所以b0 为仅有一个节点的单体树 而b1 =
		 * 2 b2 = 4 b3 = 8 个节点 所以i代表当前的树的阶数 而j代表该阶有几个节点 当到达底层时 停止循环
		 */
        for (int i = 0, j = 1; j < currentSize; i++, j = j * 2) {
            // 获取阶为i的两个二项队列中的子树
            Node<AnyType> t1 = theTrees[i];
            // 有个无聊的校验
            Node<AnyType> t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;
            // 如果本树不为空 变量+1
            int whichCase = t1 == null ? 0 : 1;
            // 合并树不为空 那么变量+2
            whichCase += t2 == null ? 0 : 2;
            // 秩为i+1的树不为空 那么变量+4
            whichCase += carry == null ? 0 : 4;
            // 开始进入选择
            switch (whichCase) {
                case 0:
                case 1:
                    // 如果没有树或者仅有本体 本阶结束
                    break;
                case 2:
                    // 如果仅有添加树
                    theTrees[i] = t2;
                    // 那么将添加树放入当前阶的队列中 同时消除添加树的该 行数据
                    rhs.theTrees[i] = null;
                    break;
                case 4:
                    // 如果仅有遗留的秩为i+1的树 那么添加即可
                    theTrees[i] = carry;
                    carry = null;
                    break;
                case 3:
                    // 如果在本秩中 具有t1 和 t2两颗同样大小的二项树
                    carry = combineTrees(t1, t2);
                    // 那么合并之后 随着秩的提高 将其延迟处理 交给下一次的循环 并将合并源数据归零
                    theTrees[i] = rhs.theTrees[i] = null;
                    break;
                case 5:
                    // 同case3 如果本秩中本体树和延迟树的秩相同
                    carry = combineTrees(t1, carry);
                    // 则再次合并 同时新生成的树的秩又变大 所以延迟处理 并将合并源数据归零
                    theTrees[i] = null;
                    break;
                case 6:
                    // 同case 5
                    carry = combineTrees(t2, carry);
                    rhs.theTrees[i] = null;
                    break;
                case 7:
				/*
				 * 这是最热闹的了 t1 t2 和 延迟树都处在同一个秩上 那么首先将延迟树付给当前秩。因为这样做是合法的
				 */
                    theTrees[i] = carry;
                    // 因为t1 和 t2 合并后 秩会变大 所以延迟树直接赋给当前二项队列没有问题
                    carry = combineTrees(t1, t2);
                    // 然后新生成的延迟树接着循环下去 并将合并源数据归零
                    rhs.theTrees[i] = null;
                    break;
            }
        }
        // 然后删除外部的二项队列
        for (int k = 0; k < rhs.theTrees.length; k++) {
            rhs.theTrees[k] = null;
        }
        rhs.currentSize = 0;
        // 至此 合并任务完成
    }

    /**
     * @param newNumTrees
     * @Descirbe 将二项队列根据参数进行重构和扩容
     * @Author Hope6537(赵鹏)
     * @Params @param newNumTrees
     * @SignDate 2014-4-5下午08:40:40
     * @Version 0.9
     */
    @SuppressWarnings("unchecked")
    private void expandTheTrees(int newNumTrees) {
        // 得到各个树的根节点的集合
        Node<AnyType>[] old = theTrees;
        // 计算总体树的数量
        int oldNumTrees = theTrees.length;
        // 声明新树的大小
        theTrees = new Node[newNumTrees];
        // 然后从0开始遍历 到老树的长度依次将原值赋回
        for (int i = 0; i < oldNumTrees; i++) {
            theTrees[i] = old[i];
        }
        // 从老树末尾开始到新扩的地方 依次
        for (int i = oldNumTrees; i < newNumTrees; i++) {
            theTrees[i] = null;
        }
    }

    /**
     * @return
     * @Descirbe 将二项队列中的最小节点的项返回
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午09:46:54
     * @Version 0.9
     */
    public AnyType deleteMin() {
        if (isEmpty()) {
            //如果队列是空的 那么直接返回null
            return null;
        }
        //否则 找到二项队列中具有最小项的树的索引
        int minIndex = findMinIndex();
        //找到索引后 直接获取根的元素就可以了 因为每棵树都符合堆序性质
        AnyType min = theTrees[minIndex].element;
        //然后获取到当前被摘掉的根的左子树
        Node<AnyType> deletedTree = theTrees[minIndex].leftChild;
        //声明新的二项队列
        BinomialQueue<AnyType> deletedQueue = new BinomialQueue<AnyType>();
        //将新声明的二项队列大小更改成(具有最小项的树)的索引+1 即后移一位
        deletedQueue.expandTheTrees(minIndex + 1);
        //然后修改新的二项队列的大小
        deletedQueue.currentSize = (1 << minIndex) - 1;
        //将该树根节点去掉后 将散开的树组成的新二项队列
        for (int j = minIndex - 1; j >= 0; j--) {
            deletedQueue.theTrees[j] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[j].nextSibling = null;
        }
        //这是将包含最小项树去掉后的原二项队列所存在的权值 将这个权值所代表的树赋NULL
        theTrees[minIndex] = null;
        //然后将二项队列中树的数量修改
        currentSize = currentSize - (deletedQueue.currentSize + 1);
        //将大队列和去掉根节点后所散开子树组成的小队列合并
        merge(deletedQueue);
        //返回最小值
        return min;

    }

    public void insert(AnyType x) {
        merge(new BinomialQueue<AnyType>(x));
    }

    public static class Node<AnyType> {
        AnyType element; // 节点元素
        Node<AnyType> leftChild; // 节点左子树
        Node<AnyType> nextSibling; // 节点右兄弟

        Node(AnyType x, Node<AnyType> l, Node<AnyType> r) {
            element = x;
            leftChild = l;
            nextSibling = r;
        }

        Node(AnyType x) {
            this(x, null, null);
        }
    }
}
