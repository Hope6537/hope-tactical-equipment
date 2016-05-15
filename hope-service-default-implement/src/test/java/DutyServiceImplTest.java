
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.DutyDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.DutyService;
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
public class DutyServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DutyService dutyService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(dutyService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = dutyService.addDuty(90,91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddDuty() {
        ResultSupport<Integer> integerResultSupport = dutyService.addDuty(90,91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyDuty() {
        ResultSupport<Integer> resultSupport = dutyService.addDuty(90,91);
        Integer id = resultSupport.getModule();
        DutyDto dto = new DutyDto(80,81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = dutyService.modifyDuty(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = dutyService.batchModifyDuty(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(dutyService.getDutyListByIdList(idList)));
    }

    @Test
    public void testRemoveDuty() {
        ResultSupport<Integer> resultSupport = dutyService.addDuty(70,71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = dutyService.removeDuty(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = dutyService.batchRemoveDuty(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(dutyService.getDutyListByIdList(idList)));
    }


    @Test
    public void testGetDutyById() {
        ResultSupport<DutyDto> result = dutyService.getDutyById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetDutyListByIdList() {
        ResultSupport<List<DutyDto>> result = dutyService.getDutyListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetDutyListByQuery() {
        DutyDto dto = new DutyDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<DutyDto>> dutyListByQuery = dutyService.getDutyListByQuery(dto);
        logger.info(JSON.toJSONString(dutyListByQuery));
        dto.setCurrentPage(2);
        dutyListByQuery = dutyService.getDutyListByQuery(dto);
        logger.info(JSON.toJSONString(dutyListByQuery));
        dto.setCurrentPage(3);
        dutyListByQuery = dutyService.getDutyListByQuery(dto);
        logger.info(JSON.toJSONString(dutyListByQuery));
    }


}

    