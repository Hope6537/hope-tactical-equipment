package org.hope6537.note.design.mix.chain_command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 命令模式+责任链模式
 * Unix命令模拟场景类
 */
public class Client {

    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Invoker invoker = new Invoker();
        while (true) {
            System.out.print("#");
            try {
                String input = reader.readLine();
                if (input.equals("quit") || input.equals("exit")) {
                    return;
                }
                System.out.println(invoker.exec(input));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
