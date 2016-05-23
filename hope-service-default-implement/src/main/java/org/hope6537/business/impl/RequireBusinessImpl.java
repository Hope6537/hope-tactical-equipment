package org.hope6537.business.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hope6537.business.RequireBusiness;
import org.hope6537.dto.*;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hope6537 on 16/5/23.
 */
@Service(value = "requireBusiness")
public class RequireBusinessImpl implements RequireBusiness {

    @Resource(name = "parentService")
    private ParentService parentService;
    @Resource(name = "studentService")
    private StudentService studentService;
    @Resource(name = "dutyService")
    private DutyService dutyService;
    @Resource(name = "requireService")
    private RequireService requireService;
    @Resource(name = "teacherService")
    private TeacherService teacherService;

    @Override
    public ResultSupport<List<RequireDto>> getRequiredListByTeacherId(Integer teacherId) {

        ResultSupport<TeacherDto> teacher = teacherService.getTeacherById(teacherId);
        if (!teacher.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询教师信息失败]");
        }
        ResultSupport<List<DutyDto>> dutyList = dutyService.getDutyListByTeacherIdList(Lists.newArrayList(teacherId));
        Map<Integer, DutyDto> dutyDtoMap = DtoUtils.indexObject(dutyList.getModule());
        if (!dutyList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询班级信息失败]");
        }
        ResultSupport<List<StudentDto>> studentList = studentService.getStudentListByClassesIdList(
                dutyList.getModule().stream().map(DutyDto::getClassesId).collect(Collectors.toList())
        );
        Map<Integer, StudentDto> studentDtoMap = DtoUtils.indexObject(studentList.getModule());
        if (!studentList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询学生信息失败]");
        }
        ResultSupport<List<RequireDto>> requireList = requireService.getRequireListByStudentIdList(Lists.newArrayList(studentDtoMap.keySet()));
        Set<Integer> parentIdSet = Sets.newConcurrentHashSet();
        if (!requireList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询需求信息失败]");
        }
        requireList.getModule().stream().forEach(requireDto -> {
            parentIdSet.add(requireDto.getParentId());
            if (requireDto.getTeacherId() != null) {
                requireDto.setTeacherName(teacher.getModule().getName());
            }
            if (requireDto.getStudentId() != null) {
                requireDto.setStudentName(studentDtoMap.get(requireDto.getStudentId()).getName());
            }
        });
        ResultSupport<List<ParentDto>> parent = parentService.getParentListByIdList(Lists.newArrayList(parentIdSet));
        if (!parent.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询家长信息失败]");
        }
        Map<Integer, ParentDto> parentDtoMap = DtoUtils.indexObject(parent.getModule());
        requireList.getModule().stream().forEach(requireDto -> {
            if (requireDto.getParentId() != null) {
                requireDto.setParentName(parentDtoMap.get(requireDto.getParentId()).getName());
            }

        });

        return requireList;
    }

    public ResultSupport<List<RequireDto>> getRequireRichListByQuery(RequireDto query) {


        ResultSupport<List<RequireDto>> requireList = requireService.getRequireListByQuery(query);
        if (!requireList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询需求信息失败]");
        }
        Set<Integer> teacherIdSet = Sets.newConcurrentHashSet();
        Set<Integer> studentIdSet = Sets.newConcurrentHashSet();
        Set<Integer> parentIdSet = Sets.newConcurrentHashSet();
        requireList.getModule().stream().forEach(requireDto -> {
            if (requireDto.getTeacherId() != null) {
                teacherIdSet.add(requireDto.getTeacherId());
            }
            if (requireDto.getStudentId() != null) {
                studentIdSet.add(requireDto.getStudentId());
            }
            if (requireDto.getParentId() != null) {
                parentIdSet.add(requireDto.getParentId());
            }
        });
        Map<Integer, StudentDto> studentDtoMap = Maps.newConcurrentMap();
        Map<Integer, ParentDto> parentDtoMap = Maps.newConcurrentMap();
        Map<Integer, TeacherDto> teacherDtoMap = Maps.newConcurrentMap();
        if (!studentIdSet.isEmpty()) {
            ResultSupport<List<StudentDto>> student = studentService.getStudentListByIdList(Lists.newLinkedList(studentIdSet));
            if (!student.isSuccess()) {
                return ResultSupport.getInstance(false, "[查询学生信息失败]");
            }
            studentDtoMap.putAll(DtoUtils.indexObject(student.getModule()));
        }
        if (!teacherIdSet.isEmpty()) {
            ResultSupport<List<TeacherDto>> teacher = teacherService.getTeacherListByIdList(Lists.newArrayList(teacherIdSet));
            if (!teacher.isSuccess()) {
                return ResultSupport.getInstance(false, "[查询教师信息失败]");
            }
            teacherDtoMap.putAll(DtoUtils.indexObject(teacher.getModule()));
        }
        if (!parentIdSet.isEmpty()) {
            ResultSupport<List<ParentDto>> parent = parentService.getParentListByIdList(Lists.newArrayList(parentIdSet));
            if (!parent.isSuccess()) {
                return ResultSupport.getInstance(false, "[查询家长信息失败]");
            }
            parentDtoMap.putAll(DtoUtils.indexObject(parent.getModule()));
        }

        requireList.getModule().stream().forEach(requireDto -> {
            if (requireDto.getParentId() != null) {
                requireDto.setParentName(parentDtoMap.get(requireDto.getParentId()).getName());
            }
            if (requireDto.getStudentId() != null) {
                requireDto.setStudentName(studentDtoMap.get(requireDto.getStudentId()).getName());
            }
            if (requireDto.getTeacherId() != null) {
                requireDto.setTeacherName(teacherDtoMap.get(requireDto.getTeacherId()).getName());
            }
        });
        return requireList;
    }


}
