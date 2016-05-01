package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.JumpDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface JumpDao {

    int insertJump(JumpDo jumpDo);

    int updateJump(JumpDo jumpDo);

    int batchUpdateJump(@Param("data") JumpDo jumpDo, @Param("idList") List<Integer> idList);

    int deleteJump(@Param("id") Integer id);

    int batchDeleteJump(@Param("idList") List<Integer> idList);

    JumpDo selectJumpById(@Param("id") Integer id);

    List<JumpDo> selectJumpListByIds(@Param("idList") List<Integer> idList);

    List<JumpDo> selectJumpListByQuery(JumpDo query);

    int selectJumpCountByQuery(JumpDo query);

}

    