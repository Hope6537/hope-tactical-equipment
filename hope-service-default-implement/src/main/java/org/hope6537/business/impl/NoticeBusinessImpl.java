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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hope6537 on 16/5/21.
 */
@Service(value = "noticeBusiness")
public class NoticeBusinessImpl implements NoticeBusiness {

    @Resource(name = "noticeService")
    private NoticeService noticeService;
    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "messageService")
    private MessageService messageService;
    @Resource(name = "studentService")
    private StudentService studentService;

    @Override
    public ResultSupport<Boolean> addNoticeWithClasses(NoticeDto notice, List<Integer> classesIdList) {
        ResultSupport<Integer> addNotice = noticeService.addNotice(notice);
        if (!addNotice.isSuccess()) {
            return ResultSupport.getInstance(false, "[添加通知失败]");
        }
        Integer id = addNotice.getModule();
        Integer successCount = 0;
        for (Integer classesId : classesIdList) {
            MessageDto messageDto = new MessageDto();
            messageDto.setClassesId(classesId);
            messageDto.setNoticeId(id);
            ResultSupport<Integer> addMessage = messageService.addMessage(messageDto);
            successCount += addMessage.isSuccess() ? 1 : 0;
        }
        boolean expr = successCount == classesIdList.size();
        return ResultSupport.getInstance(expr, expr ? "[完成通知关联]" : "[通知关联失败]", expr);

    }

    @SuppressWarnings("unchecked")
    public ResultSupport<Map<Integer, Map<String, Object>>> buildRichNoticeListByIdList(List<Integer> idList) {
        Map<Integer, Map<String, Object>> result = Maps.newConcurrentMap();
        ResultSupport<List<NoticeDto>> notice = noticeService.getNoticeListByIdList(idList);
        if (!notice.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取notice失败]", null);
        }
        Map<Integer, NoticeDto> noticeDtoMap = DtoUtils.indexObject(notice.getModule());
        ResultSupport<List<MessageDto>> message = messageService.getMessageListByNoticeIdList(notice.getModule().stream().map(NoticeDto::getId).collect(Collectors.toList()));
        if (!message.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取message失败]", null);
        }
        ResultSupport<List<ClassesDto>> classes = classesService.getClassesListByIdList(message.getModule().stream().map(MessageDto::getClassesId).collect(Collectors.toList()));
        if (!classes.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取classes失败]", null);
        }
        Map<Integer, ClassesDto> classesDtoMap = DtoUtils.indexObject(classes.getModule());
        message.getModule().forEach(messageDto -> {
            Map<String, Object> json = Maps.newConcurrentMap();
            json.put("entry", noticeDtoMap.get(messageDto.getNoticeId()));
            Object relation = json.get("relation");
            if (relation == null) {
                relation = new ArrayList<ClassesDto>();
                json.put("relation", relation);
            }
            //unchecked
            ((List<ClassesDto>) relation).add(classesDtoMap.get(messageDto.getClassesId()));
            result.put(messageDto.getNoticeId(), json);
        });
        boolean expr = !result.isEmpty();
        return ResultSupport.getInstance(expr, expr ? "[关联构建完成]" : "关联构建失败", result);
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
        noticeIndexMap = DtoUtils.indexObject(noticeList.getModule());

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
        classesIndexMap = DtoUtils.indexObject(classesList.getModule());

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
        Map<Integer, StudentDto> integerStudentDtoMap = DtoUtils.indexObject(studentList.getModule());
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
