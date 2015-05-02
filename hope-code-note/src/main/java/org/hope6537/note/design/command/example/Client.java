package org.hope6537.note.design.command.example;

import org.junit.Test;

/**
 * 命令模式场景类
 */
public class Client {

    @Test
    public void test() throws Exception {

        Invoker invoker = new Invoker();

        Receiver receiver = new RevicerImpl1();

        Command command = new CommandImpl1();
        Command command2 = new CommandImpl2(receiver);

        invoker.setCommand(command);
        invoker.action();

        invoker.setCommand(command2);
        invoker.action();
    }

}
