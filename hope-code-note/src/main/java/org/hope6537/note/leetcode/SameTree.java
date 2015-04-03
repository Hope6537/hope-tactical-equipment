package org.hope6537.note.leetcode;

import org.junit.Test;

/**
 * Created by Hope6537 on 2015/3/31.
 */
public class SameTree {

    public boolean isSameTree(TreeNode p, TreeNode q) {

        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else {
            if (p.val != q.val) {
                return false;
            } else {
                boolean left = isSameTree(p.left, q.left);
                boolean right = isSameTree(p.right, q.right);
                return left && right && p.val == q.val;
            }
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

    @Test
    public void testTrain() {
        isSameTree(new TreeNode(0), new TreeNode(0));
    }
}
