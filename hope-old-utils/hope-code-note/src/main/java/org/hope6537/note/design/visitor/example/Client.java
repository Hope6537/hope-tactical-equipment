package org.hope6537.note.design.visitor.example;

/**
 * Created by Hope6537 on 2015/4/20.
 */
public class Client {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            AbstractElement element = ObjectStruture.createElement();
            element.accept(new VisitorImpl());
        }
    }

}
