
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.PlanDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.PlanService;
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
public class PlanServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PlanService planService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(planService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = planService.addPlan("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddPlan() {
        ResultSupport<Integer> integerResultSupport = planService.addPlan("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyPlan() {
        ResultSupport<Integer> resultSupport = planService.addPlan("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), 92);
        Integer id = resultSupport.getModule();
        PlanDto dto = new PlanDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), 82);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = planService.modifyPlan(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = planService.batchModifyPlan(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(planService.getPlanListByIdList(idList)));
    }

    @Test
    public void testRemovePlan() {
        ResultSupport<Integer> resultSupport = planService.addPlan("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), 72);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = planService.removePlan(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = planService.batchRemovePlan(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(planService.getPlanListByIdList(idList)));
    }


    @Test
    public void testGetPlanById() {
        ResultSupport<PlanDto> result = planService.getPlanById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetPlanListByIdList() {
        ResultSupport<List<PlanDto>> result = planService.getPlanListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetPlanListByQuery() {
        PlanDto dto = new PlanDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<PlanDto>> planListByQuery = planService.getPlanListByQuery(dto);
        logger.info(JSON.toJSONString(planListByQuery));
        dto.setCurrentPage(2);
        planListByQuery = planService.getPlanListByQuery(dto);
        logger.info(JSON.toJSONString(planListByQuery));
        dto.setCurrentPage(3);
        planListByQuery = planService.getPlanListByQuery(dto);
        logger.info(JSON.toJSONString(planListByQuery));
    }


}

    