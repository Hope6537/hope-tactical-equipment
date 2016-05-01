package org.hope6537.note.design.template.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class Client {

    public static void main(String[] args) {
        AbstractClass abstractClass1 = new ConcreteClass1();
        AbstractClass abstractClass2 = new ConcreteClass2(true);
        abstractClass1.templateMethod();
        abstractClass2.templateMethod();
    }

}

