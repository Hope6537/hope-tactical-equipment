package org.hope6537.controller;

import org.hope6537.soa.spi.BasicService;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by hope6537 on 15/12/10.
 * Any Question sent to hope6537@qq.com
 */
public class Controller {

    private BasicService basicService;

    public void hasDemo() throws InterruptedException {
        for (int i = 0; i < 500; i++) {
            try {
                System.out.println(basicService.getTime());
            } catch (Exception e) {
                System.err.println(new Date().toString() + e.getMessage());
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    public BasicService getBasicService() {
        return basicService;
    }

    public void setBasicService(BasicService basicService) {
        this.basicService = basicService;
    }
}
