package org.hope6537.note.design.command.example;

/**
 * 命令解释器-执行器
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
