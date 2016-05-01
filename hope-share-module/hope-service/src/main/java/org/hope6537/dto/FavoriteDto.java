package org.hope6537.dto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class FavoriteDto extends BasicDto {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 目标ID
     */
    private Integer targetId;

    /**
     * 目标类型 0-漫画 1-专辑
     */
    private Integer targetType;

    /**
     * 用户收藏漫画
     * */
    private Map<UserInfoDto, List<ComicDto>> favoriteComic = new ConcurrentHashMap<>();

    /**
     * 用户收藏专辑
     * */
    private Map<UserInfoDto, List<SpecialDto>> favoriteSpecial = new ConcurrentHashMap<>();

    public FavoriteDto() {

    }

    public FavoriteDto(Integer userId, Integer targetId, Integer targetType) {

        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Map<UserInfoDto, List<ComicDto>> getFavoriteComic() {
        return favoriteComic;
    }

    public void setFavoriteComic(Map<UserInfoDto, List<ComicDto>> favoriteComic) {
        this.favoriteComic = favoriteComic;
    }

    public Map<UserInfoDto, List<SpecialDto>> getFavoriteSpecial() {
        return favoriteSpecial;
    }

    public void setFavoriteSpecial(Map<UserInfoDto, List<SpecialDto>> favoriteSpecial) {
        this.favoriteSpecial = favoriteSpecial;
    }
}

