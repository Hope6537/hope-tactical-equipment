package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.TimeUnit;

public class CPUTest {

    public static void main(String[] args) throws InterruptedException {
        for (; ; ) {
            for (int i = 0; i < 9600000; i++) {

            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }
}
