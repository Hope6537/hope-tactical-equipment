package org.hope6537.note.design.command.example;

/**
 * 具体命令
 */
public class CommandImpl2 extends Command {

    private Receiver receiver;

    public CommandImpl2(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.doSomething();
    }
}
