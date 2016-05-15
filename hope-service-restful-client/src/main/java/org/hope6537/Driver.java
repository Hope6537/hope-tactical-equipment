package org.hope6537;

import org.hope6537.controller.LiveTemplateController;
import org.hope6537.controller.LockerController;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{LockerController.class, LiveTemplateController.class};
        SpringApplication.run(classes, args);
    }

}
