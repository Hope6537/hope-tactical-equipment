package org.hope6537;

import org.hope6537.controller.ComicController;
import org.hope6537.controller.LockerController;
import org.hope6537.controller.WelcomeController;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{LockerController.class, ComicController.class, WelcomeController.class};
        SpringApplication.run(classes, args);
    }

}
