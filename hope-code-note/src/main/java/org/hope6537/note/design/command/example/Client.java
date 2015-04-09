package org.hope6537.note.design.command.example;

import org.junit.Test;

public class Client {

    @Test
    public void test() throws Exception {

        Invoker invoker = new Invoker();

        Receiver receiver = new RevicerImpl1();

        Command command = new CommandImpl1(receiver);

        invoker.setCommand(command);
        invoker.action();
    }

}
