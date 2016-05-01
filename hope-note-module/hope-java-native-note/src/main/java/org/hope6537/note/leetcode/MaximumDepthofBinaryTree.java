package org.hope6537.note.leetcode;

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

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
