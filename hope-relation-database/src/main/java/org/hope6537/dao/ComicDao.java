package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.ComicDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface ComicDao {

    int insertComic(ComicDo comicDo);

    int updateComic(ComicDo comicDo);

    int batchUpdateComic(@Param("data") ComicDo comicDo, @Param("idList") List<Integer> idList);

    int deleteComic(@Param("id") Integer id);

    int batchDeleteComic(@Param("idList") List<Integer> idList);

    ComicDo selectComicById(@Param("id") Integer id);

    List<ComicDo> selectComicListByIds(@Param("idList") List<Integer> idList);

    List<ComicDo> selectComicListByQuery(ComicDo query);

    int selectComicCountByQuery(ComicDo query);

}

    