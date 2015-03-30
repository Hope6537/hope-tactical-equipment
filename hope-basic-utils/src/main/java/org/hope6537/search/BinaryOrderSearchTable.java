package org.hope6537.search;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hope6537 on 2015/3/29.
 */
public class BinaryOrderSearchTable<Key extends Comparable<? super Key>, Value> extends OrderSearchTable<Key, Value> {

    private Key[] keys;

    private Value[] values;

    private int count;

    public BinaryOrderSearchTable(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        values = (Value[]) new Object[capacity];
        count = 0;
    }


    @Override
    public Iterable<Key> getKeys(Key lo, Key hi) {
        List<Key> keyList = new LinkedList<>();
        for (int i = rank(lo); i < rank(hi); i++) {
            keyList.add(keys[i]);
        }
        if (contains(hi)) {
            keyList.add(keys[rank(hi)]);
        }
        return keyList;
    }

    @Override
    public Key min() {
        return keys[0];
    }

    @Override
    public Key max() {
        return keys[count - 1];
    }

    @Override
    public Key floor(Key key) {
        return null;
    }

    @Override
    public Key ceiling(Key key) {
        int i = rank(key);
        return keys[i];
    }

    @Override
    public int rank(Key key) {
        int lo = 0, hi = count - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    @Override
    public Key select(int rank) {
        return keys[rank];
    }

    @Override
    public void put(Key key, Value value) {
        int i = rank(key);
        if (i < count && keys[i].compareTo(key) == 0) {
            values[i] = value;
            return;
        }
        for (int j = count; j > i; j--) {
            keys[j] = keys[j - 1];
            values[j] = values[j - i];
        }
        keys[i] = key;
        values[i] = value;
        count++;
    }

    @Override
    public Value get(Key key) {
        if (isEmpty()) {
            return null;
        }
        int i = rank(key);
        if (i < count && keys[i].compareTo(key) == 0) {
            return values[i];
        } else {
            return null;
        }
    }

    @Override
    public void delete(Key key) {

    }

    @Override
    public int size() {
        return count;
    }
}
