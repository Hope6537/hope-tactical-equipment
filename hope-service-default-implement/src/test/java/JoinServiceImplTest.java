
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.JoinDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.JoinService;
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
public class JoinServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JoinService joinService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(joinService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = joinService.addJoin(90,91);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddJoin() {
        ResultSupport<Integer> integerResultSupport = joinService.addJoin(90,91);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyJoin() {
        ResultSupport<Integer> resultSupport = joinService.addJoin(90,91);
        Integer id = resultSupport.getModule();
        JoinDto dto = new JoinDto(80,81);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = joinService.modifyJoin(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = joinService.batchModifyJoin(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(joinService.getJoinListByIdList(idList)));
    }

    @Test
    public void testRemoveJoin() {
        ResultSupport<Integer> resultSupport = joinService.addJoin(70,71);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = joinService.removeJoin(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = joinService.batchRemoveJoin(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(joinService.getJoinListByIdList(idList)));
    }


    @Test
    public void testGetJoinById() {
        ResultSupport<JoinDto> result = joinService.getJoinById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetJoinListByIdList() {
        ResultSupport<List<JoinDto>> result = joinService.getJoinListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetJoinListByQuery() {
        JoinDto dto = new JoinDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<JoinDto>> joinListByQuery = joinService.getJoinListByQuery(dto);
        logger.info(JSON.toJSONString(joinListByQuery));
        dto.setCurrentPage(2);
        joinListByQuery = joinService.getJoinListByQuery(dto);
        logger.info(JSON.toJSONString(joinListByQuery));
        dto.setCurrentPage(3);
        joinListByQuery = joinService.getJoinListByQuery(dto);
        logger.info(JSON.toJSONString(joinListByQuery));
    }


}

    