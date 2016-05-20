package org.hope6537.business.impl;

import org.hope6537.business.EventBusiness;
import org.hope6537.dto.EventDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.service.ClassesService;
import org.hope6537.service.EventService;
import org.hope6537.service.PublishService;
import org.mockito.internal.util.collections.Sets;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by hope6537 on 16/5/21.
 */
public class EventBusinessImpl implements EventBusiness {

    @Resource(name = "eventService")
    private EventService eventService;
    @Resource(name = "classesService")
    private ClassesService classesService;
    @Resource(name = "publishService")
    private PublishService publishService;

    @Override
    public ResultSupport<List<EventDto>> getEventRichListByQuery(EventDto query) {
        Set<Integer> eventIdSet = Sets.newSet();
        ResultSupport<List<EventDto>> eventListByQuery = eventService.getEventListByQuery(query);
        if (!eventListByQuery.isSuccess()) {
            return ResultSupport.getInstance(false, "获取活动失败", null);
        }
        List<EventDto> eventDtoList = eventListByQuery.getModule();
        eventDtoList.forEach(eventDto -> eventIdSet.add(eventDto.getId()));
        return null;
    }

    @Override
    public ResultSupport<List<EventDto>> getEventRichListByIdList() {
        return null;
    }
}
