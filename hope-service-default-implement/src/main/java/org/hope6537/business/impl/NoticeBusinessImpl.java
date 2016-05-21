package org.hope6537.business.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hope6537.business.NoticeBusiness;
import org.hope6537.dto.*;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ClassesService;
import org.hope6537.service.MessageService;
import org.hope6537.service.NoticeService;
import org.hope6537.service.StudentService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hope6537 on 16/5/21.
 */
public class NoticeBusinessImpl implements NoticeBusiness {

    @Resource(name = "noticeService")
    private NoticeService noticeService;
    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "messageService")
    private MessageService messageService;
    @Resource(name = "studentService")
    private StudentService studentService;


    public <T extends BasicDto> Map<Integer, T> indexObject(List<T> module) {
        Map<Integer, T> result = Maps.newConcurrentMap();
        module.forEach(t -> result.put(t.getId(), t));
        return result;
    }

    @Override
    public ResultSupport<List<NoticeDto>> getNoticeRichListByQuery(NoticeDto query) {

        Map<Integer, NoticeDto> noticeIndexMap;
        Map<Integer, ClassesDto> classesIndexMap;
        Set<Integer> classesIdSet = Sets.newConcurrentHashSet();
        Map<Integer, List<Integer>> noticeMapToClasses = Maps.newConcurrentMap();

        ResultSupport<List<NoticeDto>> noticeList = noticeService.getNoticeListByQuery(query);
        if (!noticeList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取通知失败]", null);
        }
        noticeIndexMap = indexObject(noticeList.getModule());

        ResultSupport<List<MessageDto>> messageList = messageService.getMessageListByNoticeIdList(Lists.newArrayList(noticeIndexMap.keySet()));
        if (!messageList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取消息列表失败]", null);
        }
        messageList.getModule().forEach(messageDto -> {
            List<Integer> classesIdList = noticeMapToClasses.get(messageDto.getNoticeId());
            if (classesIdList == null) {
                classesIdList = Lists.newArrayList();
                noticeMapToClasses.put(messageDto.getNoticeId(), classesIdList);
            }
            classesIdSet.add(messageDto.getClassesId());
            classesIdList.add(messageDto.getClassesId());
        });

        ResultSupport<List<ClassesDto>> classesList = classesService.getClassesListByIdList(Lists.newArrayList(classesIdSet));
        if (!classesList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取班级列表失败]", null);
        }
        classesIndexMap = indexObject(classesList.getModule());

        List<NoticeDto> result = Lists.newArrayList();

        for (NoticeDto noticeDto : noticeList.getModule()) {
            List<Integer> classesIdList = noticeMapToClasses.get(noticeDto.getId());
            for (Integer classesId : classesIdList) {
                List<ClassesDto> relationClasses = noticeDto.getRelationClasses();
                if (relationClasses == null) {
                    relationClasses = Lists.newArrayList();
                    noticeDto.setRelationClasses(relationClasses);
                }
                relationClasses.add(classesIndexMap.get(classesId));
            }
            result.add(noticeDto);
        }

        boolean expr = result.size() > 0;
        return ResultSupport.getInstance(expr, expr ? "获取通知成功" : "获取通知失败", result);

    }

    @Override
    public ResultSupport<List<NoticeDto>> getNoticeRichListByParentId(Integer parentId) {

        ResultSupport<List<StudentDto>> studentList = studentService.getStudentListByParentIdList(Lists.newArrayList(parentId));
        if (!studentList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取学生失败]", null);
        }
        Map<Integer, StudentDto> integerStudentDtoMap = indexObject(studentList.getModule());
        ResultSupport<List<MessageDto>> messageList = messageService.getMessageListByClassesIdList(studentList.getModule().stream().map(StudentDto::getClassesId).collect(Collectors.toList()));
        if (!messageList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取消息失败]", null);
        }
        ResultSupport<List<NoticeDto>> noticeListByIdList = noticeService.getNoticeListByIdList(messageList.getModule().stream().map(MessageDto::getNoticeId).collect(Collectors.toList()));
        if (!messageList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取通知失败]", null);
        }
        return noticeListByIdList;
    }
}
