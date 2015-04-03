package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.LinkedList;

public class Solution {
    private LinkedList<Object> appleList = new LinkedList<>();

    private int MAX = 5;

    public Solution() {
    }

    public void start() {
        new Adder().start();
        new Geter().start();
    }

    class Adder extends Thread {
        public void run() {
            while (true) {
                synchronized (appleList) {
                    try {
                        while (appleList.size() == MAX) {
                            appleList.wait();
                        }
                        Object newOb = new Object();
                        if (appleList.add(newOb)) {
                            System.out.println("add" + appleList.size());
                            appleList.notify();
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }

                }
            }
        }
    }

    class Geter extends Thread {
        public void run() {
            while (true) {
                synchronized (appleList) {
                    try {
                        while (appleList.size() == 0) {
                            appleList.wait();
                        }
                        appleList.removeLast();
                        System.out.println("minus" + appleList.size());
                        appleList.notify();
                    } catch (InterruptedException ie) {
                    }

                }
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Solution pc = new Solution();
        pc.start();
    }
}