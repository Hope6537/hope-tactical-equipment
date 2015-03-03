package org.hope6537.datastruct.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 作为一个可迭代和支持泛型的容器
 * 使用链表思想
 */
public class Bag<Item> implements Iterable<Item> {
    private int N;               // number of elements in bag
    private Node<Item> first;    // beginning of bag

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Initializes an empty bag.
     */
    public Bag() {
        first = null;
        N = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        N++;
    }

    /**
     * 返回一个支持foreach的内部迭代器
     */
    public Iterator<Item> iterator() {

        return new ListIterator<Item>(first);
    }

    /**
     * 这个迭代器没有实现remove方法
     */
    private class ListIterator<Item> implements Iterator<Item> {
        
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


}