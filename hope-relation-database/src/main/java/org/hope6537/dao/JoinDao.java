package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.JoinDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface JoinDao {

    int insertJoin(JoinDo joinDo);

    int updateJoin(JoinDo joinDo);

    int batchUpdateJoin(@Param("data") JoinDo joinDo, @Param("idList") List<Integer> idList);

    int deleteJoin(@Param("id") Integer id);

    int batchDeleteJoin(@Param("idList") List<Integer> idList);

    JoinDo selectJoinById(@Param("id") Integer id);

    List<JoinDo> selectJoinListByIds(@Param("idList") List<Integer> idList);

    List<JoinDo> selectJoinListByEventIds(@Param("idList") List<Integer> idList);

    List<JoinDo> selectJoinListByStudentIds(@Param("idList") List<Integer> idList);

    List<JoinDo> selectJoinListByQuery(JoinDo query);

    int selectJoinCountByQuery(JoinDo query);

}

    