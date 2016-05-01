package org.hope6537.search;

import org.hope6537.context.ApplicationConstant;

/**
 * Created by Hope6537 on 2015/3/29.
 */
public abstract class SearchTable<Key, Value> {

    /**
     * if(value == null){
     * delete(key);
     * return;
     * }
     */
    public abstract void put(Key key, Value value);

    public abstract Value get(Key key);

    /**
     * put(key,null) <- 即时删除
     */
    public abstract void delete(Key key);

    public abstract Iterable<Key> getKeys();

    public boolean contains(Key key) {
        return ApplicationConstant.notNull(get(key));
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public abstract int size();

}
