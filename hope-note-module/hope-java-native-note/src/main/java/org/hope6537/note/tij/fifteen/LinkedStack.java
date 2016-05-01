package org.hope6537.note.tij.fifteen;

/**
 * @param <T>
 * @version 0.9
 * @Describe 链式栈
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:10:59
 * @company Changchun University&SHXT
 */
public class LinkedStack<T> {

    private Node<T> top = new Node<T>();// 末端哨兵

    public static void main(String[] args) {
        LinkedStack<String> linkedStack = new LinkedStack<String>();
        for (String s : "This is Spark!".split(" ")) {
            linkedStack.push(s);
        }
        String s;
        while ((s = linkedStack.pop()) != null) {
            System.out.println(s);
        }
    }

    public void push(T item) {
        top = new Node<T>(item, top);
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        return result;
    }

    private static class Node<U> {
        U item;
        Node<U> next;

        Node() {
            item = null;
            next = null;
        }

        Node(U item, Node<U> next) {
            super();
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }
}
