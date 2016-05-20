package org.hope6537.business.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hope6537.business.EventBusiness;
import org.hope6537.dto.ClassesDto;
import org.hope6537.dto.EventDto;
import org.hope6537.dto.PublishDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ClassesService;
import org.hope6537.service.EventService;
import org.hope6537.service.PublishService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by hope6537 on 16/5/21.
 */
@Service(value = "eventBusinessImpl")
public class EventBusinessImpl implements EventBusiness {

    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "publishService")
    private PublishService publishService;

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
    public ResultSupport<List<EventDto>> getEventRichListByIdList() {
        return null;
    }
}
