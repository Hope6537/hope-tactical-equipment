package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.UserInfoDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface UserInfoDao {

    int insertUserInfo(UserInfoDo userInfoDo);

    int updateUserInfo(UserInfoDo userInfoDo);

    int batchUpdateUserInfo(@Param("data") UserInfoDo userInfoDo, @Param("idList") List<Integer> idList);

    int deleteUserInfo(@Param("id") Integer id);

    int batchDeleteUserInfo(@Param("idList") List<Integer> idList);

    UserInfoDo selectUserInfoById(@Param("id") Integer id);

    List<UserInfoDo> selectUserInfoListByIds(@Param("idList") List<Integer> idList);

    List<UserInfoDo> selectUserInfoListByQuery(UserInfoDo query);

    int selectUserInfoCountByQuery(UserInfoDo query);

}

    