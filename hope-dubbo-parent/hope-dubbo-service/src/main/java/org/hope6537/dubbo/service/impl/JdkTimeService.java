package org.hope6537.dubbo.service.impl;

import org.hope6537.dubbo.service.TimeService;

/**
 * Created by hope6537 on 15/11/11.
 */
public class JdkTimeService implements TimeService
{
    @Override
    public Long getCurrentTime() {
        return new java.util.Date().getTime();
    }
}
