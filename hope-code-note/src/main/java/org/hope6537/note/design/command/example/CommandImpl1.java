package org.hope6537.note.design.command.example;

/**
 * Created by Hope6537 on 2015/4/9.
 */
public class CommandImpl1 extends Command {

    private Receiver receiver;

    /*public CommandImpl1(Receiver receiver) {
        this.receiver = receiver;
    }
*/

    public CommandImpl1() {
        //另一种实现方式，针对特定的接收者
        this.receiver = new RevicerImpl1();
    }

    @Override
    public void execute() {
        this.receiver.doSomething();
    }
}
