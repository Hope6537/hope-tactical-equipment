package org.hope6537.business;

import org.hope6537.entity.ResultSupport;

import java.util.List;

/**
 * 各种自动生成业务
 * Created by hope6537 on 16/5/20.
 */
public interface GeneratorBusiness {

    ResultSupport<List<Integer>> generateParents(int count);

    ResultSupport<List<Integer>> generateStudents(int count);

    ResultSupport<List<Integer>> generateTeachers(int count);

    ResultSupport<List<Integer>> generateClasses(int count);

    ResultSupport<Boolean> generateClassesTeacherRelation(int count);

    ResultSupport<Boolean> generateClassesStudentRelation();

    ResultSupport<Boolean> generateParentStudentRelation();

}
