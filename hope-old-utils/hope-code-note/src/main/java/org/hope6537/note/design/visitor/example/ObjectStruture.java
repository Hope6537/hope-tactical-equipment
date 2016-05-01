package org.hope6537.note.design.visitor.example;

import java.util.Random;

/**
 * Created by Hope6537 on 2015/4/20.
 */
public class ObjectStruture {

    public static AbstractElement createElement() {
        Random random = new Random();
        if (random.nextInt(100) > 50) {
            return new Element1();
        } else {
            return new Element2();
        }
    }

}
