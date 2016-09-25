package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.ApplyDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface ApplyDao {

    int insertApply(ApplyDo applyDo);

    int updateApply(ApplyDo applyDo);

    int batchUpdateApply(@Param("data") ApplyDo applyDo, @Param("idList") List<Integer> idList);

    int deleteApply(@Param("id") Integer id);

    int batchDeleteApply(@Param("idList") List<Integer> idList);

    ApplyDo selectApplyById(@Param("id") Integer id);

    List<ApplyDo> selectApplyListByIds(@Param("idList") List<Integer> idList);

    List<ApplyDo> selectApplyListByTeacherIds(@Param("idList") List<Integer> idList);

    List<ApplyDo> selectApplyListByRequiredIds(@Param("idList") List<Integer> idList);

    List<ApplyDo> selectApplyListByQuery(ApplyDo query);

    int selectApplyCountByQuery(ApplyDo query);

}

    