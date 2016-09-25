package org.hope6537.business.impl;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import org.hope6537.business.GeneratorBusiness;
import org.hope6537.dto.*;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据生成业务
 * Created by hope6537 on 16/5/20.
 */
@Service(value = "generatorBusiness")
public class GeneratorBusinessImpl implements GeneratorBusiness {

    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "parentService")
    private ParentService parentService;
    @Resource(name = "studentService")
    private StudentService studentService;
    @Resource(name = "teacherService")
    private TeacherService teacherService;
    @Resource(name = "dutyService")
    private DutyService dutyService;

    @Override
    public ResultSupport<List<Integer>> generateParents(int count) {
        return parentService.generatorParents(count);
    }

    @Override
    public ResultSupport<List<Integer>> generateStudents(int count) {
        return studentService.generatorStudents(count);
    }

    @Override
    public ResultSupport<List<Integer>> generateTeachers(int count) {
        return teacherService.generatorTeachers(count);
    }

    @Override
    public ResultSupport<List<Integer>> generateClasses(int count) {
        return classesService.generatorClasses(count);
    }

    @Override
    public ResultSupport<Boolean> generateClassesTeacherRelation(int count) {
        try {
            Map<Integer, Integer> classesIdSet = new ConcurrentHashMap<>();
            Map<Integer, Integer> teacherIdSet = new ConcurrentHashMap<>();
            //首先查询出所有的班级和教师
            ResultSupport<List<ClassesDto>> classesListByQuery = classesService.getClassesListByQuery(new ClassesDto());
            ResultSupport<List<TeacherDto>> teacherListByQuery = teacherService.getTeacherListByQuery(new TeacherDto());
            //根据班级查询学生数量
            List<ClassesDto> classesModule = classesListByQuery.getModule();
            List<TeacherDto> teacherModule = teacherListByQuery.getModule();
            //筛选出当前教师少于5的班级
            for (ClassesDto classesDto : classesModule) {
                DutyDto dutyDtoQuery = new DutyDto();
                dutyDtoQuery.setClassesId(classesDto.getId());
                ResultSupport<List<DutyDto>> dutyListByQuery = dutyService.getDutyListByQuery(dutyDtoQuery);
                List<DutyDto> dutyList = dutyListByQuery.getModule();
                if (dutyList.size() < 5) {
                    classesIdSet.put(classesDto.getId(), dutyList.size());
                }
            }
            //筛选出当前班级数量少于5的教师
            for (TeacherDto teacherDto : teacherModule) {
                DutyDto dutyDtoQuery = new DutyDto();
                dutyDtoQuery.setTeacherId(teacherDto.getId());
                ResultSupport<List<DutyDto>> dutyListByQuery = dutyService.getDutyListByQuery(dutyDtoQuery);
                List<DutyDto> dutyList = dutyListByQuery.getModule();
                if (dutyList.size() < 5) {
                    teacherIdSet.put(teacherDto.getId(), dutyList.size());
                }
            }
            Set<Integer> teacher = teacherIdSet.keySet();
            Integer[] teacherIdArray = teacher.toArray(new Integer[teacher.size()]);
            Set<Integer> classes = classesIdSet.keySet();
            Integer[] classesIdArray = classes.toArray(new Integer[classes.size()]);

            Random random = new Random();
            Integer c = count;
            Integer successCount = 0;
            while (c-- != 0) {
                Integer tId = teacherIdArray[random.nextInt(teacherIdArray.length - 1)];
                Integer cId = classesIdArray[random.nextInt(classesIdArray.length - 1)];
                ResultSupport<Integer> result = dutyService.addDuty(cId, tId);
                successCount += result.isSuccess() ? 1 : 0;
            }
            //完成
            boolean expr = successCount > 0;
            return ResultSupport.getInstance(expr, expr ? "教师分配完成" : "教师分配失败", expr);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSupport.getInstance(e);
        }
    }

    @Override
    public ResultSupport<Boolean> generateClassesStudentRelation() {
        try {
            Map<Integer, Integer> classesIdSet = new ConcurrentHashMap<>();
            //首先查询出所有的班级和教师
            ResultSupport<List<ClassesDto>> classesListByQuery = classesService.getClassesListByQuery(new ClassesDto());
            //根据班级查询学生数量
            List<ClassesDto> classesModule = classesListByQuery.getModule();


            //查询还没有班级的学生
            StudentDto query = new StudentDto();
            query.setClassesId(0);
            ResultSupport<List<StudentDto>> studentListByQuery = studentService.getStudentListByQuery(query);

            //如果小于30个人的班级,符合条件,可以加入
            for (ClassesDto classesDto : classesModule) {
                query = new StudentDto();
                query.setClassesId(classesDto.getId());
                ResultSupport<List<StudentDto>> dutyListByQuery = studentService.getStudentListByQuery(query);
                List<StudentDto> studentDtoList = dutyListByQuery.getModule();
                if (studentDtoList.size() < 30) {
                    classesIdSet.put(classesDto.getId(), studentDtoList.size());
                }
            }
            //拆出班级Id Array
            Set<Integer> classes = classesIdSet.keySet();
            Integer[] classesIdArray = classes.toArray(new Integer[classes.size()]);
            Random random = new Random();
            Integer successCount = 0;

            //进行分班
            for (StudentDto studentDto : studentListByQuery.getModule()) {
                studentDto.setClassesId(classesIdArray[random.nextInt(classesIdArray.length - 1)]);
                ResultSupport<Integer> result = studentService.modifyStudent(studentDto);
                successCount += result.isSuccess() ? 1 : 0;
            }
            boolean expr = successCount > 0;
            return ResultSupport.getInstance(expr, expr ? "学生分班完成" : "学生分班失败", expr);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSupport.getInstance(e);
        }
    }

    @Override
    public ResultSupport<Boolean> generateParentStudentRelation() {
        try {
            //查询所有学生
            ResultSupport<List<StudentDto>> studentListByQuery = studentService.getStudentListByQuery(new StudentDto());
            List<StudentDto> studentDtos = studentListByQuery.getModule();
            //查询已经有家长的学生的家长ID,在之后的分配中排除掉
            Set<Integer> alreadyHaveStudentParentId = new ConcurrentHashSet<>();
            for (StudentDto studentDto : studentDtos) {
                if (studentDto.getParentId() != 0) {
                    alreadyHaveStudentParentId.add(studentDto.getParentId());
                }
            }
            //查询还没有学生的家长
            ResultSupport<List<ParentDto>> parentListByQuery = parentService.getParentListByQuery(new ParentDto());
            List<ParentDto> parentDtos = parentListByQuery.getModule();
            Set<Integer> noStudentParentId = new ConcurrentHashSet<>();
            for (ParentDto parentDto : parentDtos) {
                if (!alreadyHaveStudentParentId.contains(parentDto.getId())) {
                    noStudentParentId.add(parentDto.getId());
                }
            }
            Random random = new Random();
            Integer successCount = 0;
            Integer[] noStudentParentIdArray = noStudentParentId.toArray(new Integer[noStudentParentId.size()]);
            for (StudentDto studentDto : studentDtos) {
                if (studentDto.getParentId() == 0) {
                    studentDto.setParentId(noStudentParentIdArray[random.nextInt(noStudentParentIdArray.length - 1)]);
                    ResultSupport<Integer> result = studentService.modifyStudent(studentDto);
                    successCount += result.isSuccess() ? 1 : 0;
                }
            }
            boolean expr = successCount > 0;
            return ResultSupport.getInstance(expr, expr ? "学生家长关联完成" : "学生家长关联失败", expr);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultSupport.getInstance(e);
        }
    }
}
