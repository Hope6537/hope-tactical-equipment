package org.hope6537.basic.service;


import org.hope6537.basic.dao.BasicDao;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface BasicService<T, DaoType extends BasicDao<T>> {

    boolean addEntry(T t);

    boolean updateEntry(T t);

    boolean disableEntry(T t);

    boolean deleteEntry(T t);

    T getEntryById(String id);

    List<T> getEntryListByEntry(T t);

}
