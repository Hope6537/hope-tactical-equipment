package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.EventDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface EventDao {

    int insertEvent(EventDo eventDo);

    int updateEvent(EventDo eventDo);

    int batchUpdateEvent(@Param("data") EventDo eventDo, @Param("idList") List<Integer> idList);

    int deleteEvent(@Param("id") Integer id);

    int batchDeleteEvent(@Param("idList") List<Integer> idList);

    EventDo selectEventById(@Param("id") Integer id);

    List<EventDo> selectEventListByIds(@Param("idList") List<Integer> idList);

    List<EventDo> selectEventListByQuery(EventDo query);

    int selectEventCountByQuery(EventDo query);

}

    