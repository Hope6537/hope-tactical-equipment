package org.hope6537.search;

import org.hope6537.context.ApplicationConstant;

/**
 * 红黑树2-3平衡树
 */
public class RedBlackBinarySearchTree<Key extends Comparable<? super Key>, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = true;

    private Node root;

    /**
     * 红黑树节点判定
     */
    private boolean isRed(Node x) {
        if (ApplicationConstant.isNull(x)) {
            return false;
        }
        return x.color = RED;
    }

    public void put(Key key, Value value) {

    }

    public Node put(Node h, Key key, Value value) {
        if (ApplicationConstant.isNull(h)) {
            //标准的插入操作，和父节点用红连接相连
            return new Node(key, value, 1, RED);
        }
        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, value);
        } else if (cmp > 0) {
            h.right = put(h.right, key, value);
        } else {
            h.value = value;
        }

        if (isRed(h.right) && isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        h.n = size(h.left) + size(h.right) + 1;
        return h;
    }

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (ApplicationConstant.isNull(x)) {
            return 0;
        } else {
            return x.n;
        }
    }

    /**
     * 左旋转h
     */
    protected Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 转换颜色来分解节点
     */
    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 右旋转h
     */
    protected Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * 红黑树的节点表示
     */
    private class Node {
        Key key;
        Value value;
        Node left, right;
        int n;
        boolean color;

        public Node(Key key, Value value, int n, boolean color) {
            this.key = key;
            this.value = value;
            this.n = n;
            this.color = color;
        }
    }
}
