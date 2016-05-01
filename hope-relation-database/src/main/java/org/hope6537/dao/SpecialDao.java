package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.SpecialDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface SpecialDao {

    int insertSpecial(SpecialDo specialDo);

    int updateSpecial(SpecialDo specialDo);

    int batchUpdateSpecial(@Param("data") SpecialDo specialDo, @Param("idList") List<Integer> idList);

    int deleteSpecial(@Param("id") Integer id);

    int batchDeleteSpecial(@Param("idList") List<Integer> idList);

    SpecialDo selectSpecialById(@Param("id") Integer id);

    List<SpecialDo> selectSpecialListByIds(@Param("idList") List<Integer> idList);

    List<SpecialDo> selectSpecialListByQuery(SpecialDo query);

    int selectSpecialCountByQuery(SpecialDo query);

}

    