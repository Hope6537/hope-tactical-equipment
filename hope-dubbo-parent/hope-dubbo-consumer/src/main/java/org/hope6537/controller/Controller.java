package org.hope6537.controller;

import org.hope6537.soa.spi.BasicService;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hope6537 on 15/12/10.
 * Any Question sent to hope6537@qq.com
 */
public class Controller {

    private BasicService basicService;

    public void hasDemo() throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                for (int j = 0; j < Integer.MAX_VALUE; j++) {
                    try {
                        System.out.println(Thread.currentThread().getName() + ":" + basicService.getTime());
                    } catch (Exception e) {
                        System.err.println(new Date().toString() + e.getMessage());
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public BasicService getBasicService() {
        return basicService;
    }

    public void setBasicService(BasicService basicService) {
        this.basicService = basicService;
    }
}
