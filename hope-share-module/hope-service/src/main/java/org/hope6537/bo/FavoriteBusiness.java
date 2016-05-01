package org.hope6537.bo;

import org.hope6537.dto.FavoriteDto;
import org.hope6537.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/14.
 */
public interface FavoriteBusiness {

    /**
     * 获取用户收藏漫画
     *
     *@param favoriteDto query-用户ID
     *@return ResultSupport.module = 用户收藏漫画
     * */
    ResultSupport<FavoriteDto> getUserFavoriteComics(FavoriteDto favoriteDto);


    /**
     * 获取用户收藏专辑
     *
     * @param favoriteDto query-用户ID
     * @return ResultSupport.module = 用户收藏专辑
     * */
    ResultSupport<FavoriteDto> getUserFavoriteSpecials(FavoriteDto favoriteDto);

}
