package org.hope6537.note.leetcode;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Hope6537 on 2015/3/31.
 */
public class MaximumDepthofBinaryTree {

    /**
     * Definition for binary tree
     * public class TreeNode {
     * int val;
     * TreeNode left;
     * TreeNode right;
     * TreeNode(int x) { val = x; }
     * }
     * <p>
     * 如果一棵树只有一个结点，它的深度为1。如果根结点只有左子树而没有右子树，那么树的深度应该是其左子树的深度加1；
     * 同样如果根结点只有右子树而没有左子树，那么树的深度应该是其右子树的深度加1。如果既有右子树又有左子树呢？那该树的深度就是其左、右子树深度的较大值再加1。
     */
    public int maxDepth(TreeNode root) {
        if (root != null) {
            if (root.left == null && root.right == null) {
                return 1;
            } else if (root.left != null && root.right == null) {
                return 1 + maxDepth(root.left);
            } else if (root.left == null) {
                return 1 + maxDepth(root.right);
            } else {
                return 1 + Math.max(maxDepth(root.right), maxDepth(root.left));
            }
        } else {
            return 0;
        }
    }


    public static int maxDepthWithBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }

        int depth = 0;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int count = 0;
            TreeNode peek = queue.peek();
            if (peek.left != null) {
                count++;
            }
            if (peek.right != null) {
                count++;
            }
            if (count == 0) {
                queue.poll();
            }
            depth++;
            while (count > 0) {
                TreeNode poll = queue.poll();
                if (poll.left != null) {
                    queue.add(poll.left);
                }
                if (poll.right != null) {
                    queue.add(poll.right);
                }
                count--;
            }
        }
        return depth;
    }

    public int maxDepthBFS(TreeNode root) {
        Queue<Pair<TreeNode, Integer>> stack = new LinkedList<>();
        if (root != null) {
            stack.add(new Pair(root, 1));
        }

        int depth = 0;
        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> current = stack.poll();
            root = current.getKey();
            int current_depth = current.getValue();
            if (root != null) {
                depth = Math.max(depth, current_depth);
                stack.add(new Pair(root.left, current_depth + 1));
                stack.add(new Pair(root.right, current_depth + 1));
            }
        }
        return depth;
    }


    public static void main(String[] args) {

        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);

        a.left = b;
        b.left = c;
        c.left = d;
        c.right = e;

        int i = maxDepthWithBFS(a);
        System.out.println(i);

    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
