package org.hope6537;

import org.hope6537.controller.*;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{LockerController.class, ComicController.class, WelcomeController.class,
                ClassifiedController.class, MineController.class, SearchController.class, SpecialController.class,
                UserInfoController.class};
        SpringApplication.run(classes, args);
    }

}
