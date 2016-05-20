
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.MealDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.MealService;
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
public class MealServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MealService mealService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(mealService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = mealService.addMeal("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis());
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddMeal() {
        ResultSupport<Integer> integerResultSupport = mealService.addMeal("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis());
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyMeal() {
        ResultSupport<Integer> resultSupport = mealService.addMeal("test0" + System.currentTimeMillis(), "test1" + System.currentTimeMillis(), "test2" + System.currentTimeMillis(), "test3" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        MealDto dto = new MealDto("modify0" + System.currentTimeMillis(), "modify1" + System.currentTimeMillis(), "modify2" + System.currentTimeMillis(), "modify3" + System.currentTimeMillis());
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = mealService.modifyMeal(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = mealService.batchModifyMeal(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(mealService.getMealListByIdList(idList)));
    }

    @Test
    public void testRemoveMeal() {
        ResultSupport<Integer> resultSupport = mealService.addMeal("wait_delete0" + System.currentTimeMillis(), "wait_delete1" + System.currentTimeMillis(), "wait_delete2" + System.currentTimeMillis(), "wait_delete3" + System.currentTimeMillis());
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = mealService.removeMeal(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = mealService.batchRemoveMeal(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(mealService.getMealListByIdList(idList)));
    }


    @Test
    public void testGetMealById() {
        ResultSupport<MealDto> result = mealService.getMealById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetMealListByIdList() {
        ResultSupport<List<MealDto>> result = mealService.getMealListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetMealListByQuery() {
        MealDto dto = new MealDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<MealDto>> mealListByQuery = mealService.getMealListByQuery(dto);
        logger.info(JSON.toJSONString(mealListByQuery));
        dto.setCurrentPage(2);
        mealListByQuery = mealService.getMealListByQuery(dto);
        logger.info(JSON.toJSONString(mealListByQuery));
        dto.setCurrentPage(3);
        mealListByQuery = mealService.getMealListByQuery(dto);
        logger.info(JSON.toJSONString(mealListByQuery));
    }


}

    