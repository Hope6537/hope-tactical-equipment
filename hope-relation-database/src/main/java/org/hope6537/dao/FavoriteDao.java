package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.FavoriteDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface FavoriteDao {

    int insertFavorite(FavoriteDo favoriteDo);

    int updateFavorite(FavoriteDo favoriteDo);

    int batchUpdateFavorite(@Param("data") FavoriteDo favoriteDo, @Param("idList") List<Integer> idList);

    int deleteFavorite(@Param("id") Integer id);

    int batchDeleteFavorite(@Param("idList") List<Integer> idList);

    FavoriteDo selectFavoriteById(@Param("id") Integer id);

    List<FavoriteDo> selectFavoriteListByIds(@Param("idList") List<Integer> idList);

    List<FavoriteDo> selectFavoriteListByQuery(FavoriteDo query);

    int selectFavoriteCountByQuery(FavoriteDo query);

}

    