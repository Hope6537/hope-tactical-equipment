package org.hope6537.search;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hope6537 on 2015/3/29.
 */
public class LinkedSearchTable<Key, Value> extends SearchTable<Key, Value> {

    private Node first;

    @Override
    public void put(Key key, Value value) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
    }

    @Override
    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return x.value;
            }
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        put(key, null);
    }

    @Override
    public Iterable<Key> getKeys() {
        Set<Key> keys = new HashSet<>(size());
        for (Node x = first; x != null; x = x.next) {
            keys.add(x.key);
        }
        return keys;
    }

    @Override
    public int size() {
        int size = 0;
        for (Node x = first; x != null; x = x.next) {
            size++;
        }
        return size;
    }

    private class Node {
        Key key;
        Value value;
        Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
