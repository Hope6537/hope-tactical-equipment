package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.DutyDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface DutyDao {

    int insertDuty(DutyDo dutyDo);

    int updateDuty(DutyDo dutyDo);

    int batchUpdateDuty(@Param("data") DutyDo dutyDo, @Param("idList") List<Integer> idList);

    int deleteDuty(@Param("id") Integer id);

    int batchDeleteDuty(@Param("idList") List<Integer> idList);

    DutyDo selectDutyById(@Param("id") Integer id);

    List<DutyDo> selectDutyListByIds(@Param("idList") List<Integer> idList);

    List<DutyDo> selectDutyListByClassesIds(@Param("idList") List<Integer> idList);

    List<DutyDo> selectDutyListByTeacherIds(@Param("idList") List<Integer> idList);

    List<DutyDo> selectDutyListByQuery(DutyDo query);

    int selectDutyCountByQuery(DutyDo query);

}

    