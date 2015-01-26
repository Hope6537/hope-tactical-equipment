package org.hope6537.note.thinking_in_java.fifteen;

/**
 * @Describe 链式栈
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:10:59
 * @version 0.9
 * @company Changchun University&SHXT
 * @param <T>
 */
public class LinkedStack<T> {

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

	private Node<T> top = new Node<T>();// 末端哨兵

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
}
