package org.hope6537.note.thinking_in_java.fifteen;

import java.util.ArrayList;
import java.util.List;

interface Processor<T, E extends Exception> {
    /**
     * @Descirbe 声明抛出异常的泛型方法？
     */
    void process(List<T> resultCollector) throws E;
}

class ProcessRunner<T, E extends Exception> extends ArrayList<Processor<T, E>> {

    private static final long serialVersionUID = -5223728820744571273L;

    List<T> processAll() throws E {
        List<T> resultCollector = new ArrayList<T>();
        for (Processor<T, E> processor : this) {
            processor.process(resultCollector);
        }
        return resultCollector;
    }
}

class Failure1 extends Exception {

    private static final long serialVersionUID = -7154036090096500659L;

}

class Failure2 extends Exception {

    private static final long serialVersionUID = -7154011110096500659L;

}

class Processoer1 implements Processor<String, Failure1> {
    static int count = 3;

    @Override
    public void process(List<String> resultCollector) throws Failure1 {
        if (count-- > 1) {
            resultCollector.add("Hep!");
        } else {
            resultCollector.add("Ho!");
        }
        if (count < 0) {
            throw new Failure1();
        }
    }
}

class Processoer2 implements Processor<Integer, Failure2> {
    static int count = 2;

    @Override
    public void process(List<Integer> resultCollector) throws Failure2 {
        if (count-- == 0) {
            resultCollector.add(47);
        } else {
            resultCollector.add(11);
        }
        if (count < 0) {
            throw new Failure2();
        }
    }
}

public class ThrowGenericException {

    public static void main(String[] args) {
        ProcessRunner<String, Failure1> runner = new ProcessRunner<String, Failure1>();
        for (int i = 0; i < 3; i++) {
            runner.add(new Processoer1());
        }
        try {
            System.out.println(runner.processAll());
        } catch (Failure1 e) {
            e.printStackTrace();
        }
        ProcessRunner<Integer, Failure2> runner2 = new ProcessRunner<Integer, Failure2>();
        for (int i = 0; i < 3; i++) {
            runner2.add(new Processoer2());
        }
        try {
            System.out.println(runner2.processAll());
        } catch (Failure2 e) {
            e.printStackTrace();
        }
    }
}
