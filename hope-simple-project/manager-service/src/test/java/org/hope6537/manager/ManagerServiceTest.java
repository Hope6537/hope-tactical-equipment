package org.hope6537.manager;

import org.apache.log4j.spi.LoggerFactory;
import org.hope6537.context.ApplicationConstant;
import org.hope6537.context.SpringTestHelper;
import org.hope6537.manager.dataobject.ManagerDO;
import org.hope6537.manager.service.ManagerService;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by hope6537 on 15/11/22.
 * Any Question sent to hope6537@qq.com
 */
@ContextConfiguration("classpath:spring/spring-core.xml")
public class ManagerServiceTest extends SpringTestHelper {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ManagerServiceTest.class);

    @Autowired
    private ManagerService managerService;

    ManagerDO appendEntry() {
        return new ManagerDO("name", "account", "password", ApplicationConstant.STATUS_NORMAL);
    }

    @Test
    public void testAdd() {
        ManagerDO managerDO = appendEntry();
        boolean b = managerService.addEntry(managerDO);
        assertTrue(b);
    }

    @Test
    public void testGet() {
        ManagerDO managerDO = appendEntry();
        boolean b = managerService.addEntry(managerDO);
        assertTrue(b);
        logger.debug(managerDO.toString());
        ManagerDO entryById = managerService.getEntryById(managerDO.getId());
        assertNotNull(entryById);
        assertEquals(entryById.getAccount(), managerDO.getAccount());

    }

    @Test
    public void testUpdate() {
        ManagerDO managerDO = appendEntry();
        boolean b = managerService.addEntry(managerDO);
        assertTrue(b);
        assertNotNull(managerDO.commonId());
        logger.debug(managerDO.toString());
        managerDO.setAccount(managerDO.getAccount() + "_update");

        b = managerService.updateEntry(managerDO);
        assertTrue(b);

        ManagerDO entryById = managerService.getEntryById(managerDO.getId());
        logger.debug(entryById.toString());
        assertNotNull(entryById);
        assertEquals(entryById.getAccount(), "account_update");
    }


}
