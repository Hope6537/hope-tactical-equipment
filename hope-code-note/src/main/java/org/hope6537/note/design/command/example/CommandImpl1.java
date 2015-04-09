package org.hope6537.note.design.command.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class CommandImpl1 extends Command {

    private Receiver receiver;

    public CommandImpl1(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        this.receiver.doSomething();
    }
}
