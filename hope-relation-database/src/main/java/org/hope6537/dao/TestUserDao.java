package org.hope6537.dao;

import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.TestUserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface TestUserDao {

    int insertTestUser(TestUserDo testUserDo);

    int updateTestUser(TestUserDo testUserDo);

    int batchUpdateTestUser(@Param("data") TestUserDo testUserDo, @Param("idList") List<Integer> idList);

    int deleteTestUser(@Param("id") Integer id);

    int batchDeleteTestUser(@Param("idList") List<Integer> idList);

    TestUserDo selectTestUserById(@Param("id") Integer id);

    List<TestUserDo> selectTestUserListByIds(@Param("idList") List<Integer> idList);

    List<TestUserDo> selectTestUserListByQuery(TestUserDo query);

    int selectTestUserCountByQuery(TestUserDo query);

}

    