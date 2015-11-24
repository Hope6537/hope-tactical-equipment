package org.hope6537.note.thinking_in_java.twenty_one;

import java.math.BigInteger;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * Created by hope6537 on 15/11/24.
 * Any Question sent to hope6537@qq.com
 */
public class PrimeProvider extends Thread {


    private final BlockingQueue<BigInteger> deque;


    public PrimeProvider(BlockingQueue<BigInteger> deque) {
        this.deque = deque;
    }

    @Override
    public void run() {
        try {
            BigInteger bigInteger = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                deque.put(bigInteger.nextProbablePrime());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        interrupt();
    }
}
