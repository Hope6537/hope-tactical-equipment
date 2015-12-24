package org.hope6537.soa.spi.impl;

import org.hope6537.soa.spi.BasicService;

/**
 * Created by hope6537 on 15/12/10.
 * Any Question sent to hope6537@qq.com
 */
public class BasicServiceImpl implements BasicService {
    public Long getTime() {
        return System.currentTimeMillis();
    }
}
