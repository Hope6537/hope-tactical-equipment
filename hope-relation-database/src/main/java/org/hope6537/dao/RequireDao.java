package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.RequireDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface RequireDao {

    int insertRequire(RequireDo requireDo);

    int updateRequire(RequireDo requireDo);

    int batchUpdateRequire(@Param("data") RequireDo requireDo, @Param("idList") List<Integer> idList);

    int deleteRequire(@Param("id") Integer id);

    int batchDeleteRequire(@Param("idList") List<Integer> idList);

    RequireDo selectRequireById(@Param("id") Integer id);

    List<RequireDo> selectRequireListByIds(@Param("idList") List<Integer> idList);

    List<RequireDo> selectRequireListByQuery(RequireDo query);

    int selectRequireCountByQuery(RequireDo query);

}

    