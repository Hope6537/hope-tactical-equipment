package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.UserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface UserDao {

    UserDo selectUserById(@Param("id") Integer id);

    List<UserDo> selectUserListByIds(@Param("ids") Integer... idList);

    List<UserDo> selectUserByNickname(@Param("nickname") String nickname);

    void insertUser(UserDo userDo);

    void updateUser(UserDo userDo);

    void deleteUserByIds(@Param("ids") Integer... ids);
}