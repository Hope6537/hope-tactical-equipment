package org.hope6537.note.leetcode;

import java.util.Stack;

public class TreeIterator {

    static class Node {

        int val;

        Node left;

        Node right;

        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    // 1,2,4,4,5,5,2,3,6,6,7,7,1

    private static void frontPrint(Node head) {

        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();

        Node cur = head;

        while (cur != null || !stack.isEmpty()) {

            while (cur != null) {
                System.out.print(cur.val + "->");
                stack.push(cur);
                cur = cur.left;
            }

            if (!stack.isEmpty()) {
                cur = stack.pop();
                cur = cur.right;
            }

        }
    }

    private static void midPrint(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Node cur = head;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            if (!stack.isEmpty()) {
                cur = stack.pop();
                System.out.print(cur.val + "->");
                cur = cur.right;
            }
        }
    }

    private static void backPrint(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        Stack<Integer> valStack = new Stack<>();
        int flag = 1;

        Node cur = head;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                valStack.push(0);
                cur = cur.left;
            }
            while (!stack.isEmpty() && valStack.peek() == flag) {
                valStack.pop();
                Node pop = stack.pop();
                System.out.print(pop.val + "->");
            }
            if (!stack.isEmpty()) {
                valStack.pop();
                valStack.push(1);
                cur = stack.peek();
                cur = cur.right;
            }
        }
    }



    public static void main(String[] args) {
        Node head = new Node(1, new Node(2, new Node(4), new Node(5)), new Node(3, new Node(6), new Node(7)));
        frontPrint(head);
        System.out.println("");
        midPrint(head);
        System.out.println("");
        backPrint(head);
    }

}
