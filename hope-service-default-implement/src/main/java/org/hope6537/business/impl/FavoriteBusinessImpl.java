package org.hope6537.business.impl;

import org.hope6537.bo.FavoriteBusiness;
import org.hope6537.dto.ComicDto;
import org.hope6537.dto.FavoriteDto;
import org.hope6537.dto.SpecialDto;
import org.hope6537.dto.UserInfoDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ComicService;
import org.hope6537.service.FavoriteService;
import org.hope6537.service.SpecialService;
import org.hope6537.service.UserInfoService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dintama on 2016/3/14.
 */
@Service("favoriteBusiness")
public class FavoriteBusinessImpl implements FavoriteBusiness {

    private static final int TYPE_COMIC = 0;
    private static final int TYPE_SPECIAL = 1;

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "favoriteService")
    private FavoriteService favoriteService;

    private ResultSupport<List<Integer>> getUserFavoriteTargetId(Integer userId, Integer targetType){
        /*整理得到一个FavoriteDto*/
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(userId);
        favoriteDto.setTargetType(targetType);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(favoriteDto);
        if(!favoriteListByQuery.isSuccess()){
            return favoriteListByQuery.castToReturnFailed(null);
        }
        List<FavoriteDto> favoriteDtos = favoriteListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(FavoriteDto tmp : favoriteDtos){
            idList.add(tmp.getTargetId());
        }
        return ResultSupport.getInstance(true, "[targetId查询成功]", idList);
    }

    @Override
    public ResultSupport<FavoriteDto> getUserFavoriteComics(FavoriteDto favoriteDto) {
        Integer userId = favoriteDto.getUserId();
        /*获取Key*/
        ResultSupport<UserInfoDto> userInfoById = userInfoService.getUserInfoById(userId);
        if(!userInfoById.isSuccess()){
            return userInfoById.castToReturnFailed(FavoriteDto.class);
        }
        ResultSupport<List<Integer>> userFavoriteTargetId = getUserFavoriteTargetId(userId, TYPE_COMIC);
        if(!userFavoriteTargetId.isSuccess()){
            return userFavoriteTargetId.castToReturnFailed(FavoriteDto.class);
        }
        List<Integer> idList = userFavoriteTargetId.getModule();
        ResultSupport<List<ComicDto>> comicListByIdList = comicService.getComicListByIdList(idList);
        if(!comicListByIdList.isSuccess()){
            return comicListByIdList.castToReturnFailed(FavoriteDto.class);
        }
        Map<UserInfoDto, List<ComicDto>> result = new ConcurrentHashMap<>();
        result.put(userInfoById.getModule(), comicListByIdList.getModule());
        FavoriteDto favoriteDto1 = new FavoriteDto();
        favoriteDto1.setFavoriteComic(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", favoriteDto1);
    }

    @Override
    public ResultSupport<FavoriteDto> getUserFavoriteSpecials(FavoriteDto favoriteDto) {
        Integer userId = favoriteDto.getUserId();
        /*获取Key*/
        ResultSupport<UserInfoDto> userInfoById = userInfoService.getUserInfoById(userId);
        if(!userInfoById.isSuccess()){
            return userInfoById.castToReturnFailed(FavoriteDto.class);
        }
        ResultSupport<List<Integer>> userFavoriteTargetId = getUserFavoriteTargetId(userId, TYPE_COMIC);
        if(!userFavoriteTargetId.isSuccess()){
            return userFavoriteTargetId.castToReturnFailed(FavoriteDto.class);
        }
        List<Integer> idList = userFavoriteTargetId.getModule();
        ResultSupport<List<SpecialDto>> specialListByIdList = specialService.getSpecialListByIdList(idList);
        if(!specialListByIdList.isSuccess()){
            return specialListByIdList.castToReturnFailed(FavoriteDto.class);
        }
        Map<UserInfoDto, List<SpecialDto>> result = new ConcurrentHashMap<>();
        result.put(userInfoById.getModule(), specialListByIdList.getModule());
        FavoriteDto favoriteDto1 = new FavoriteDto();
        favoriteDto1.setFavoriteSpecial(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", favoriteDto1);
    }
}
