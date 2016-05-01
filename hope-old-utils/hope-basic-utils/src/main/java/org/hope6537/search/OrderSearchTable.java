package org.hope6537.search;


/**
 * Created by Hope6537 on 2015/3/29.
 */
public abstract class OrderSearchTable<Key extends Comparable<? super Key>, Value> extends SearchTable<Key, Value> {


    @Override
    public Iterable<Key> getKeys() {
        return getKeys(min(), max());
    }

    public abstract Iterable<Key> getKeys(Key lo, Key hi);

    public abstract Key min();

    public abstract Key max();

    public abstract Key floor(Key key);

    public abstract Key ceiling(Key key);

    public abstract int rank(Key key);

    public abstract Key select(int rank);

    public void deleteMin() {
        delete(min());
    }

    public void deleteMax() {
        delete(max());
    }

    public int size(Key lo, Key hi) {
        if (hi.compareTo(lo) < 0) {
            return 0;
        } else if (contains(hi)) {
            return rank(hi) - rank(lo) + 1;
        } else {
            return rank(hi) - rank(lo);
        }
    }


}
