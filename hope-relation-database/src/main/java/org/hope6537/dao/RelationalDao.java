package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Dintama on 2016/3/12.
 */
@MybatisRepository
public interface RelationalDao {

    List<Integer> selectComicIdsByClassifiedId(@Param("id") Integer id);

    List<Integer> selectClassifiedIdsByComicId(@Param("id") Integer id);

}
