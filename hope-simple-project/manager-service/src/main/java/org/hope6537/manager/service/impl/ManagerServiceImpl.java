package org.hope6537.manager.service.impl;

import org.hope6537.basic.service.impl.BasicServiceImpl;
import org.hope6537.manager.dao.ManagerDao;
import org.hope6537.manager.dataobject.ManagerDO;
import org.hope6537.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by hope6537 on 15/11/22.
 * Any Question sent to hope6537@qq.com
 */
@Service("managerService")
public class ManagerServiceImpl extends BasicServiceImpl<ManagerDO, ManagerDao> implements ManagerService {

    @Autowired
    @Qualifier(value = "managerDao")
    @Override
    public void setDao(ManagerDao dao) {
        super.setDao(dao);
    }


}
