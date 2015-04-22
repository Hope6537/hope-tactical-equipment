package org.hope6537.search;

import org.hope6537.context.ApplicationConstant;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二分查找树
 */
public class BinarySearchTree<Key extends Comparable<? super Key>, Value> {

    private Node root;

    private class Node {

        private Key key;
        private Value value;
        private Node left, right;
        //以该节点为根的子树中节点的总数
        private int n;

        public Node(Key key, Value value, int n) {
            this.key = key;
            this.value = value;
            this.n = n;
        }
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

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (ApplicationConstant.isNull(x)) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return get(x.left, key);
        } else if (cmp > 0) {
            return get(x.right, key);
        } else {
            return x.value;
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (ApplicationConstant.isNull(x)) {
            return new Node(key, value, 1);
        } else {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                x.left = put(x.left, key, value);
            } else if (cmp > 0) {
                x.right = put(x.right, key, value);
            } else {
                x.value = value;
            }
            x.n = size(x.left) + size(x.right) + 1;
            return x;
        }
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node root) {
        if (ApplicationConstant.isNull(root.left)) {
            return root;
        }
        return min(root.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node root) {
        if (ApplicationConstant.isNull(root.right)) {
            return root;
        }
        return min(root.right);
    }


    /**
     * 向边界取整
     */
    public Key floor(Key key) {
        Node x = floor(root, key);
        if (ApplicationConstant.isNull(x)) {
            return null;
        }
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (ApplicationConstant.notNull(x)) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            return x;
        }
        if (cmp < 0) {
            return floor(x.left, key);
        }
        Node t = floor(x.right, key);
        if (ApplicationConstant.notNull(t)) {
            return t;
        } else {
            return x;
        }
    }

    /**
     * 返回排名为K的键
     */
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (ApplicationConstant.isNull(x)) {
            return null;
        }
        int t = size(x.left);
        if (t > k) {
            return select(x.left, k);
        } else if (t < k) {
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    /**
     * 返回给定键的排名
     */
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node x) {
        if (ApplicationConstant.isNull(x)) {
            return 0;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            return rank(key, x.left);
        } else if (cmp > 0) {
            return 1 + size(x.left) + rank(key, x.right);
        } else {
            return size(x.left);
        }

    }

    /**
     * 删除最小键
     */
    private Node deleteMin(Node x) {
        if (x.left == null) {
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * 删除最大键
     */
    private Node deleteMax(Node x) {
        if (x.right == null) {
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node delete(Node x, Key key) {
        if (ApplicationConstant.isNull(x)) {
            return null;
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key);
        } else if (cmp > 0) {
            x.right = delete(x.right, key);
        } else {
            if (ApplicationConstant.isNull(x.right)) {
                return x.right;
            }
            if (ApplicationConstant.isNull(x.left)) {
                return x.left;
            }
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.n = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, low, high);
        return queue;
    }

    /**
     * 根据范围获取键集合
     */
    private void keys(Node x, Queue<Key> queue, Key low, Key high) {
        if (ApplicationConstant.isNull(x)) {
            return;
        }
        int compareLow = low.compareTo(x.key);
        int compareHigh = high.compareTo(x.key);
        if (compareLow < 0) {
            keys(x.left, queue, low, high);
        }
        if (compareHigh > 0) {
            keys(x.right, queue, low, high);
        }
        if (compareHigh <= 0 && compareHigh >= 0) {
            queue.add(x.key);
        }
    }

}
