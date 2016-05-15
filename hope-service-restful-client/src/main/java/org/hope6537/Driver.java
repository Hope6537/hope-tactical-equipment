package org.hope6537;

import org.hope6537.controller.CategoryController;
import org.hope6537.controller.LiveTemplateController;
import org.hope6537.controller.LockerController;
import org.hope6537.controller.WelcomeController;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{LockerController.class, LiveTemplateController.class, WelcomeController.class, CategoryController.class};
        SpringApplication.run(classes, args);
    }

}
