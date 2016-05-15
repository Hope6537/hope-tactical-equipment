
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.EventDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.EventService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class EventServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EventService eventService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(eventService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = eventService.addEvent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddEvent() {
        ResultSupport<Integer> integerResultSupport = eventService.addEvent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyEvent() {
        ResultSupport<Integer> resultSupport = eventService.addEvent("test0"+System.currentTimeMillis(),"test1"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        EventDto dto = new EventDto("modify0"+System.currentTimeMillis(),"modify1"+System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = eventService.modifyEvent(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = eventService.batchModifyEvent(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(eventService.getEventListByIdList(idList)));
    }

    @Test
    public void testRemoveEvent() {
        ResultSupport<Integer> resultSupport = eventService.addEvent("wait_delete0"+System.currentTimeMillis(),"wait_delete1"+System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = eventService.removeEvent(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = eventService.batchRemoveEvent(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(eventService.getEventListByIdList(idList)));
    }


    @Test
    public void testGetEventById() {
        ResultSupport<EventDto> result = eventService.getEventById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetEventListByIdList() {
        ResultSupport<List<EventDto>> result = eventService.getEventListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetEventListByQuery() {
        EventDto dto = new EventDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<EventDto>> eventListByQuery = eventService.getEventListByQuery(dto);
        logger.info(JSON.toJSONString(eventListByQuery));
        dto.setCurrentPage(2);
        eventListByQuery = eventService.getEventListByQuery(dto);
        logger.info(JSON.toJSONString(eventListByQuery));
        dto.setCurrentPage(3);
        eventListByQuery = eventService.getEventListByQuery(dto);
        logger.info(JSON.toJSONString(eventListByQuery));
    }


}

    