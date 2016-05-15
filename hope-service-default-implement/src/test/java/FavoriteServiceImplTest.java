
    import com.alibaba.fastjson.JSON;
import org.hope6537.dto.FavoriteDto;
import org.hope6537.entity.ResultSupport;
import org.hope6537.helper.SpringTestHelper;
import org.hope6537.service.FavoriteService;
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
public class FavoriteServiceImplTest extends SpringTestHelper {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FavoriteService favoriteService;

    private List<Integer> idList;

    @Before
    public void init() {
        logger.info(favoriteService.toString());
        idList = Lists.newArrayList();
        for (int i = 0; i < 5; i++) {
            ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(90,91,92);
            logger.info(JSON.toJSONString(integerResultSupport));
            assertTrue(integerResultSupport.getModule() > 0);
            idList.add(integerResultSupport.getModule());
        }
        System.out.println(idList.toString());
        logger.info(idList.toString());
    }

    @Test
    public void testAddFavorite() {
        ResultSupport<Integer> integerResultSupport = favoriteService.addFavorite(90,91,92);
        logger.info(JSON.toJSONString(integerResultSupport));
        assertTrue(integerResultSupport.getModule() > 0);
    }

    @Test
    public void testModifyFavorite() {
        ResultSupport<Integer> resultSupport = favoriteService.addFavorite(90,91,92);
        Integer id = resultSupport.getModule();
        FavoriteDto dto = new FavoriteDto(80,81,82);
        dto.setId(id);
        ResultSupport<Integer> modifyResultSupport = favoriteService.modifyFavorite(dto);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = favoriteService.batchModifyFavorite(dto, idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(favoriteService.getFavoriteListByIdList(idList)));
    }

    @Test
    public void testRemoveFavorite() {
        ResultSupport<Integer> resultSupport = favoriteService.addFavorite(70,71,72);
        Integer id = resultSupport.getModule();
        ResultSupport<Integer> modifyResultSupport = favoriteService.removeFavorite(id);
        logger.info(JSON.toJSONString(modifyResultSupport));
        assertTrue(modifyResultSupport.getModule() == 1);
        ResultSupport<Integer> batchModifyResultSupport = favoriteService.batchRemoveFavorite(idList);
        logger.info(JSON.toJSONString(batchModifyResultSupport));
        assertTrue(batchModifyResultSupport.getModule() == 5);
        logger.info(JSON.toJSONString(favoriteService.getFavoriteListByIdList(idList)));
    }


    @Test
    public void testGetFavoriteById() {
        ResultSupport<FavoriteDto> result = favoriteService.getFavoriteById(idList.get(0));
        assertNotNull(result.getModule());
        String json = JSON.toJSONString(result);
        logger.info(json);
    }

    @Test
    public void testGetFavoriteListByIdList() {
        ResultSupport<List<FavoriteDto>> result = favoriteService.getFavoriteListByIdList(idList);
        assertNotNull(result.getModule());
        String jsonList = JSON.toJSONString(result);
        logger.info(jsonList);
    }

    @Test
    public void testGetFavoriteListByQuery() {
        FavoriteDto dto = new FavoriteDto();
        dto.setPageSize(2);
        dto.setCurrentPage(1);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
        dto.setCurrentPage(2);
        favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
        dto.setCurrentPage(3);
        favoriteListByQuery = favoriteService.getFavoriteListByQuery(dto);
        logger.info(JSON.toJSONString(favoriteListByQuery));
    }


}

    