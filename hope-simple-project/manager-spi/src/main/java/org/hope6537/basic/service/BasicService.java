package org.hope6537.basic.service;


import org.hope6537.basic.dao.BasicDao;

import java.util.List;

/**
 * Created by Hope6537 on 2015/3/10.
 */
public interface BasicService<T, DaoType extends BasicDao<T>> {

    int addEntry(T t);

    int batchAddEntryList(List<T> tList);

    int updateEntry(T t);

    int batchUpdateEntry(List<T> tList);

    int disableEntry(T t);

    int batchDisableEntry(List<T> tList);

    int deleteEntry(T t);

    int batchDeleteEntry(List<T> tList);

    T getEntryById(String id);

    List<T> getEntryListByEntry(T t);

}
