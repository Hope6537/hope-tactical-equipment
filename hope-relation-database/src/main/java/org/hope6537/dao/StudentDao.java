package org.hope6537.dao;

import org.apache.ibatis.annotations.Param;
import org.hope6537.annotation.MybatisRepository;
import org.hope6537.dataobject.StudentDo;

import java.util.List;

/**
 * 实体数据访问接口
 * Created by hope6537 by Code Generator
 */
@MybatisRepository
public interface StudentDao {

    int insertStudent(StudentDo studentDo);

    int updateStudent(StudentDo studentDo);

    int batchUpdateStudent(@Param("data") StudentDo studentDo, @Param("idList") List<Integer> idList);

    int deleteStudent(@Param("id") Integer id);

    int batchDeleteStudent(@Param("idList") List<Integer> idList);

    StudentDo selectStudentById(@Param("id") Integer id);

    List<StudentDo> selectStudentListByIds(@Param("idList") List<Integer> idList);

    List<StudentDo> selectStudentListByQuery(StudentDo query);

    int selectStudentCountByQuery(StudentDo query);

}

    