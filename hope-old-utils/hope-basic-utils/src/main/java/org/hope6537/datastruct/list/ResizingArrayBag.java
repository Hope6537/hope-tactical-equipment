package org.hope6537.datastruct.list;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * 使用线性表构造的Bag
 */
public class ResizingArrayBag<Item> implements Iterable<Item> {
    private Item[] a;
    private int N = 0;

    public ResizingArrayBag() {
        a = (Item[]) new Object[2];
    }


    public boolean isEmpty() {
        return N == 0;
    }


    public int size() {
        return N;
    }

    /**
     * 就是复制
     */
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }


    public void add(Item item) {
        if (N == a.length) resize(2 * a.length);
        a[N++] = item;
    }


    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i++];
        }
    }


}