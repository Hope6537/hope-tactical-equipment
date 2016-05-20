package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.TeacherDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface TeacherDao {

    int insertTeacher(TeacherDo teacherDo);

    int updateTeacher(TeacherDo teacherDo);

    int batchUpdateTeacher(@Param("data") TeacherDo teacherDo, @Param("idList") List<Integer> idList);

    int deleteTeacher(@Param("id") Integer id);

    int batchDeleteTeacher(@Param("idList") List<Integer> idList);

    TeacherDo selectTeacherById(@Param("id") Integer id);

    List<TeacherDo> selectTeacherListByIds(@Param("idList") List<Integer> idList);

    List<TeacherDo> selectTeacherListByQuery(TeacherDo query);

    int selectTeacherCountByQuery(TeacherDo query);

}

    