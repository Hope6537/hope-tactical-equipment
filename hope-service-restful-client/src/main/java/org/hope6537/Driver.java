package org.hope6537;

import org.hope6537.controller.*;
import org.springframework.boot.SpringApplication;

/**
 * Spring Boot启动器
 */
public class Driver {

    public static void main(String[] args) {
        Class[] classes = new Class[]{LockerController.class,
                ApplyController.class,
                ClassesController.class,
                DutyController.class,
                EventController.class,
                FeedbackController.class,
                JoinController.class,
                MealController.class,
                MessageController.class,
                NoticeController.class,
                ParentController.class,
                PlanController.class,
                RequireController.class,
                StudentController.class,
                PublishController.class,
                TeacherController.class,
                GeneratorController.class
        };
        SpringApplication.run(classes, args);
    }

}
