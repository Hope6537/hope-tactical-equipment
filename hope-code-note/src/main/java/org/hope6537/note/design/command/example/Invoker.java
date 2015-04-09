package org.hope6537.note.design.command.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class Invoker {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        this.command.execute();
    }
}
