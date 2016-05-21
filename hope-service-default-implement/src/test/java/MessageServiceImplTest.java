
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.MessageDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * 实体服务测试类
 * Created by hope6537 by Code Generator
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class MessageServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageService messageService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(messageService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = messageService.addMessage(90, 91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddMessage() {
        ResultSupport<Integer> integerResultSupport = messageService.addMessage(90, 91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyMessage() {
        ResultSupport<Integer> resultSupport = messageService.addMessage(90, 91);
        Integer id = resultSupport.getModule();
        MessageDto dto = new MessageDto(80, 81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = messageService.modifyMessage(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = messageService.batchModifyMessage(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(messageService.getMessageListByIdList(idList)));
    }

    @Test
    public void testRemoveMessage() {
        ResultSupport<Integer> resultSupport = messageService.addMessage(70, 71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = messageService.removeMessage(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = messageService.batchRemoveMessage(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(messageService.getMessageListByIdList(idList)));
    }


    @Test
    public void testGetMessageById() {
        ResultSupport<MessageDto> result = messageService.getMessageById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetMessageListByIdList() {
        ResultSupport<List<MessageDto>> result = messageService.getMessageListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetMessageListByQuery() {
        MessageDto dto = new MessageDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<MessageDto>> messageListByQuery = messageService.getMessageListByQuery(dto);
        logger.info(JSON.toJSONString(messageListByQuery));
        dto.setCurrentPage(2);
        messageListByQuery = messageService.getMessageListByQuery(dto);
        logger.info(JSON.toJSONString(messageListByQuery));
        dto.setCurrentPage(3);
        messageListByQuery = messageService.getMessageListByQuery(dto);
        logger.info(JSON.toJSONString(messageListByQuery));
    }


}

    