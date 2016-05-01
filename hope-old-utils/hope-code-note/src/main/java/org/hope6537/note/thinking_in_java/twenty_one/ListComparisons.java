package org.hope6537.note.thinking_in_java.twenty_one;

//: concurrency/ListComparisons.java
//{Args: 1 10 10} (Fast verification check during build)
//Rough comparison of thread-safe List performance.

import org.hope6537.note.thinking_in_java.seventeen.CountingIntegerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class ListTest extends Tester<List<Integer>> {
    ListTest(String testId, int nReaders, int nWriters) {
        super(testId, nReaders, nWriters);
    }

    void startReadersAndWriters() {
        for (int i = 0; i < nReaders; i++)
            exec.execute(new Reader());
        for (int i = 0; i < nWriters; i++)
            exec.execute(new Writer());
    }

    class Reader extends TestTask {
        long result = 0;

        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    result += testContainer.get(index);
        }

        void putResults() {
            readResult += result;
            readTime += duration;
        }
    }

    class Writer extends TestTask {
        void test() {
            for (long i = 0; i < testCycles; i++)
                for (int index = 0; index < containerSize; index++)
                    testContainer.set(index, writeData[index]);
        }

        void putResults() {
            writeTime += duration;
        }
    }
}

class SynchronizedArrayListTest extends ListTest {
    SynchronizedArrayListTest(int nReaders, int nWriters) {
        super("Synched ArrayList", nReaders, nWriters);
    }

    List<Integer> containerInitializer() {
        return Collections.synchronizedList(new ArrayList<Integer>(
                new CountingIntegerList(containerSize)));
    }
}

class CopyOnWriteArrayListTest extends ListTest {
    CopyOnWriteArrayListTest(int nReaders, int nWriters) {
        super("CopyOnWriteArrayList", nReaders, nWriters);
    }

    List<Integer> containerInitializer() {
        return new CopyOnWriteArrayList<Integer>(new CountingIntegerList(
                containerSize));
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 乐观锁和传统锁的实例测试
 * @signdate 2014年8月16日下午6:40:28
 * @company Changchun University&SHXT
 */
public class ListComparisons {
    public static void main(String[] args) {
        Tester.initMain(args);
        new SynchronizedArrayListTest(10, 0);
        new SynchronizedArrayListTest(9, 1);
        new SynchronizedArrayListTest(5, 5);
        new CopyOnWriteArrayListTest(10, 0);
        new CopyOnWriteArrayListTest(9, 1);
        new CopyOnWriteArrayListTest(5, 5);
        Tester.exec.shutdown();
    }
}
