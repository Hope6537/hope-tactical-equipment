
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.hope6537.dto.CategoryDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.CategoryService;
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
public class CategoryServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CategoryService categoryService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(categoryService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = categoryService.addCategory(90, 91, 92);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddCategory() {
        ResultSupport<Integer> integerResultSupport = categoryService.addCategory(90, 91, 92);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyCategory() {
        ResultSupport<Integer> resultSupport = categoryService.addCategory(90, 91, 92);
        Integer id = resultSupport.getModule();
        CategoryDto dto = new CategoryDto(80, 81, 82);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = categoryService.modifyCategory(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = categoryService.batchModifyCategory(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(categoryService.getCategoryListByIdList(idList)));
    }

    @Test
    public void testRemoveCategory() {
        ResultSupport<Integer> resultSupport = categoryService.addCategory(70, 71, 72);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = categoryService.removeCategory(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = categoryService.batchRemoveCategory(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(categoryService.getCategoryListByIdList(idList)));
    }


    @Test
    public void testGetCategoryById() {
        ResultSupport<CategoryDto> result = categoryService.getCategoryById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetCategoryListByIdList() {
        ResultSupport<List<CategoryDto>> result = categoryService.getCategoryListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetCategoryListByQuery() {
        CategoryDto dto = new CategoryDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(dto);
        logger.info(JSON.toJSONString(categoryListByQuery));
        dto.setCurrentPage(2);
        categoryListByQuery = categoryService.getCategoryListByQuery(dto);
        logger.info(JSON.toJSONString(categoryListByQuery));
        dto.setCurrentPage(3);
        categoryListByQuery = categoryService.getCategoryListByQuery(dto);
        logger.info(JSON.toJSONString(categoryListByQuery));
    }


}

    