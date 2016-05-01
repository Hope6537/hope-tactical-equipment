package org.hope6537.basic.dao;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface BasicDao<T> {

    int addEntry(T t);

    int updateEntry(T t);

    int disableEntry(T t);

    int deleteEntry(T t);

    T getEntryById(String id);

    List<T> getEntryListByEntry(T t);

}
