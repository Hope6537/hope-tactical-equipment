package org.hope6537.business.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.hope6537.business.EventBusiness;
import org.hope6537.dto.*;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hope6537 on 16/5/21.
 */
@Service(value = "eventBusiness")
public class EventBusinessImpl implements EventBusiness {

    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "publishService")
    private PublishService publishService;
    @Resource(name = "studentService")
    private StudentService studentService;
    @Resource(name = "joinService")
    private JoinService joinService;

    @Override
    public ResultSupport<List<EventDto>> getEventRichListByQuery(EventDto query) {
        List<EventDto> result = Lists.newArrayList();
        Map<Integer, EventDto> eventIndexMap = Maps.newConcurrentMap();
        Map<Integer, ClassesDto> classesIndexMap = Maps.newConcurrentMap();
        ResultSupport<List<EventDto>> eventListByQuery = eventService.getEventListByQuery(query);
        if (!eventListByQuery.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取活动失败]", null);
        }
        List<EventDto> eventDtoList = eventListByQuery.getModule();
        eventDtoList.forEach(eventDto -> {
            eventIndexMap.put(eventDto.getId(), eventDto);
        });
        ResultSupport<List<PublishDto>> publishListByEventIdList = publishService.getPublishListByEventIdList(Lists.newArrayList(eventIndexMap.keySet()));
        if (!publishListByEventIdList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取关联失败]", null);
        }
        ClassesDto tmp = new ClassesDto();
        List<PublishDto> publishDtoList = publishListByEventIdList.getModule();
        publishDtoList.forEach(publishDto -> classesIndexMap.put(publishDto.getClassesId(), tmp));
        ResultSupport<List<ClassesDto>> classesListByIdList = classesService.getClassesListByIdList(Lists.newArrayList(classesIndexMap.keySet()));
        if (!classesListByIdList.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取班级失败]", null);
        }
        List<ClassesDto> classesDtoList = classesListByIdList.getModule();
        classesDtoList.forEach(classesDto -> classesIndexMap.put(classesDto.getId(), classesDto));

        for (PublishDto publishDto : publishDtoList) {
            EventDto eventDto = eventIndexMap.get(publishDto.getEventId());
            List<ClassesDto> relationClasses = eventDto.getRelationClasses();
            if (relationClasses == null) {
                relationClasses = Lists.newArrayList();
                eventDto.setRelationClasses(relationClasses);
            }
            relationClasses.add(classesIndexMap.get(publishDto.getClassesId()));
        }
        eventIndexMap.forEach((integer, eventDto) -> {
            result.add(eventDto);
        });
        boolean expr = result.size() > 0;
        return ResultSupport.getInstance(expr, expr ? "[查询成功]" : "[查询失败]", result);
    }

    @Override
    public ResultSupport<List<StudentDto>> getEventRichListByParentIdGroupByStudentId(Integer parentId) {
        Map<Integer, List<Integer>> publishMap = Maps.newConcurrentMap();
        Map<Integer, EventDto> eventIndexMap = Maps.newConcurrentMap();
        Map<String, JoinDto> joinStudentEventIndexMap = Maps.newConcurrentMap();

        ResultSupport<List<StudentDto>> studentListByParentId = studentService.getStudentListByParentIdList(Lists.newArrayList(parentId));
        Set<Integer> classesId = Sets.newConcurrentHashSet();
        if (!studentListByParentId.isSuccess()) {
            return ResultSupport.getInstance(false, "[获取关联学生失败]", null);
        }
        studentListByParentId.getModule().forEach(studentDto -> classesId.add(studentDto.getClassesId()));
        ResultSupport<List<PublishDto>> publishListByClassesIdList = publishService.getPublishListByClassesIdList(Lists.newArrayList(classesId));
        if (!publishListByClassesIdList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询关联活动失败]", null);
        }
        Set<Integer> eventIdList = Sets.newConcurrentHashSet();
        publishListByClassesIdList.getModule().forEach(publishDto -> {
            eventIdList.add(publishDto.getEventId());
            List<Integer> currentEventIdList = publishMap.get(publishDto.getClassesId());
            if (currentEventIdList == null) {
                currentEventIdList = Lists.newArrayList();
                publishMap.put(publishDto.getClassesId(), currentEventIdList);
            }
            currentEventIdList.add(publishDto.getEventId());
        });

        ResultSupport<List<EventDto>> eventListByIdList = eventService.getEventListByIdList(Lists.newArrayList(eventIdList));
        if (!eventListByIdList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询活动信息失败]", null);
        }
        eventListByIdList.getModule().forEach(eventDto -> eventIndexMap.put(eventDto.getId(), eventDto));
        //查询完成了活动之后,进行和Student的关联
        //查询关联
        ResultSupport<List<JoinDto>> joinListByEventIdList = joinService.getJoinListByEventIdList(Lists.newArrayList(eventIdList));
        if (!joinListByEventIdList.isSuccess()) {
            return ResultSupport.getInstance(false, "[查询活动参与信息失败]", null);
        }
        joinListByEventIdList.getModule().forEach(joinDto -> {
            joinStudentEventIndexMap.put(joinDto.getStudentId() + ":" + joinDto.getEventId(), joinDto);
        });

        List<StudentDto> result = Lists.newArrayList();
        studentListByParentId.getModule().forEach(studentDto -> {
            Integer currentClassesId = studentDto.getClassesId();
            List<Integer> currentEventIdList = publishMap.get(currentClassesId);
            List<EventDto> relationEventList = studentDto.getRelationEventList();
            if (relationEventList == null) {
                relationEventList = Lists.newArrayList();
                studentDto.setRelationEventList(relationEventList);
            }
            for (Integer eventId : currentEventIdList) {
                JoinDto joinDto = joinStudentEventIndexMap.get(studentDto.getId() + ":" + eventId);
                EventDto e = eventIndexMap.get(eventId);
                EventDto clone = JSON.parseObject(JSON.toJSONString(e), EventDto.class);
                //获取活动参加状态
                if (joinDto != null) {
                    clone.setJoinId(joinDto.getId());
                    clone.setJoinStatus(joinDto.getStatus());
                }
                relationEventList.add(clone);
            }
            result.add(studentDto);
        });
        boolean expr = result.size() > 0;
        return ResultSupport.getInstance(expr, expr ? "查询活动成功" : "查询活动失败", result);
    }
}
