package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.TestComicDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface TestComicDao {

    int insertTestComic(TestComicDo testComicDo);

    int updateTestComic(TestComicDo testComicDo);

    int batchUpdateTestComic(@Param("data") TestComicDo testComicDo, @Param("idList") List<Integer> idList);

    int deleteTestComic(@Param("id") Integer id);

    int batchDeleteTestComic(@Param("idList") List<Integer> idList);

    TestComicDo selectTestComicById(@Param("id") Integer id);

    List<TestComicDo> selectTestComicListByIds(@Param("idList") List<Integer> idList);

    List<TestComicDo> selectTestComicListByQuery(TestComicDo query);

    int selectTestComicCountByQuery(TestComicDo query);

}
