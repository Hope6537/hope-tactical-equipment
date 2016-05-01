package org.hope6537.note.tij.test;

import java.util.PriorityQueue;
import java.util.Random;

public class Seven {

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        Random random = new Random(47);
        for (int i = 0; i < 10; i++) {
            Integer r = random.nextInt(i + 40);
            queue.offer(r);
        }
        while (queue.peek() != null) {
            System.out.print(queue.poll() + " ");
        }
    }

}
