
package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.ClassifiedDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface ClassifiedDao {

    int insertClassified(ClassifiedDo classifiedDo);

    int updateClassified(ClassifiedDo classifiedDo);

    int batchUpdateClassified(@Param("data") ClassifiedDo classifiedDo, @Param("idList") List<Integer> idList);

    int deleteClassified(@Param("id") Integer id);

    int batchDeleteClassified(@Param("idList") List<Integer> idList);

    ClassifiedDo selectClassifiedById(@Param("id") Integer id);

    List<ClassifiedDo> selectClassifiedListByIds(@Param("idList") List<Integer> idList);

    List<ClassifiedDo> selectClassifiedListByQuery(ClassifiedDo query);

    int selectClassifiedCountByQuery(ClassifiedDo query);

}

    