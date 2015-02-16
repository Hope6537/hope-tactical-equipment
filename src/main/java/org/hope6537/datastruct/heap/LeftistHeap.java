package org.hope6537.datastruct.heap;


/**
 * @param <AnyType>
 * @version 0.9
 * @Describe 左式堆
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-4-5下午04:24:19
 * @company Changchun University&SHXT
 */
public class LeftistHeap<AnyType extends Comparable<? super AnyType>> {

    /**
     * @Describe 根节点
     */
    private Node<AnyType> root;

    private static <AnyType> void swapChildren(Node<AnyType> t) {
        Node<AnyType> tmp = t.left;
        t.left = t.right;
        t.right = tmp;
    }

    /**
     * @return
     * @Descirbe 检查是否为空
     * @Author Hope6537(赵鹏)
     * @Params @return
     * @SignDate 2014-4-5下午04:44:57
     * @Version 0.9
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @Descirbe 清空左式堆
     * @Author Hope6537(赵鹏)
     * @Params
     * @SignDate 2014-4-5下午04:45:07
     * @Version 0.9
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * @param rhs
     * @Descirbe 合并例程第一步
     * @Author Hope6537(赵鹏)
     * @Params @param rhs 另外的树
     * @SignDate 2014-4-5下午04:53:55
     * @Version 0.9
     */
    public void merge(LeftistHeap<AnyType> rhs) {
        //如果本树和要合并的树相同
        if (this == rhs) {
            //则直接跳转
            return;
        }
        //获取合并后的树根 用于当本对象的新树根
        root = merge(root, rhs.root);
        //消除旧树
        rhs.root = null;
    }

    /**
     * @param h1
     * @param h2
     * @return
     * @Descirbe 合并历程第二步 树根节点操作
     * @Author Hope6537(赵鹏)
     * @Params @param h1
     * @Params @param h2
     * @Params @return
     * @SignDate 2014-4-5下午04:53:51
     * @Version 0.9
     */
    private Node<AnyType> merge(Node<AnyType> h1, Node<AnyType> h2) {
        //如果两棵树中有一颗为空 那么另一颗就是合并结果
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }
        //如果都不为空
        if (h1.element.compareTo(h2.element) < 0) {
            //同时本树的树根小于合并树 则直接传值
            return merge2(h1, h2);
            //返回的是两树合并后新的树根
        } else {
            //反正 则由小的那一部分作为新树 树根
            return merge(h2, h1);
        }

    }

    /**
     * @param h1
     * @param h2
     * @return
     * @Descirbe 合并历程第三步
     * @Author Hope6537(赵鹏)
     * @Params @param h1 参数为数值小的树根
     * @Params @param h2 参数为数字大的树根
     * @Params @return
     * @SignDate 2014-4-5下午04:57:04
     * @Version 0.9
     */
    private Node<AnyType> merge2(Node<AnyType> h1, Node<AnyType> h2) {
        //如果 h1的左节点为空  即h1是个单儿子或者无儿子节点
        if (h1.left == null) {
            //那么h2连同子树直接插入到h1的左侧
            h1.left = h2;
        } else {
            /*
             * 如果h1不是一个单节点 则将h1较小树的右子树和h2进行比较归并比较
			 * 最后在递归中 有一颗子树会遍历地仅剩下单节点 这时递归回来时 走的是上面if
			 * 这样 就讲两个散树插入合并成一个树了。
			 * 在原有H1 H2树上形成了新的结构
			 * 由于递归的性质 会再进行比较 直到变成某个根节点没有右儿子
			 * 这样 将H2'合并的新树插入进H1的右儿子里 这样合并完成 
			 */
            h1.right = merge(h1.right, h2);

            if (h1.left.npl < h1.right.npl) {
				/*
				 * 在合并的过程中 如果左节点的零差小于右节点的零差
				 * 那么该节点左右子树交换
				 */
                swapChildren(h1);
            }
			/*
			 * 在合并成功的例程里 将合并的零差根据右子树插入的情况+1
			 */
            h1.npl = h1.right.npl + 1;
        }
        return h1;
    }

    public void insert(AnyType x) {
        root = merge(new Node<AnyType>(x), root);
    }

    public AnyType deleteMin() {
        if (isEmpty()) {
            return null;
        }
        AnyType min = root.element;
        root = merge(root.left, root.right);
        return min;
    }

    /**
     * @param <AnyType>
     * @version 0.9
     * @Describe 左式堆节点
     * @Author Hope6537(赵鹏)
     * @Signdate 2014-4-5下午04:25:18
     * @company Changchun University&SHXT
     */
    private static class Node<AnyType> {
        AnyType element;
        Node<AnyType> left;
        Node<AnyType> right;
        /**
         * @Describe 零路径长
         */
        int npl;

        public Node(AnyType x) {
            this(x, null, null);
        }

        public Node(AnyType x, Node<AnyType> lf, Node<AnyType> rf) {
            this.element = x;
            this.left = lf;
            this.right = rf;
            this.npl = 0;
        }
    }
}
