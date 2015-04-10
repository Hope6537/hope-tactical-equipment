package org.hope6537.note.design.watcher.example;

import org.junit.Test;

public class Client {

    /**
     * 输出
     * >进行了些业务逻辑
     * >接收
     */
    @Test
    public void test() {
        SubjectImpl subject = new SubjectImpl();
        Observer observer = new ObserverImpl();
        subject.addObserver(observer);
        subject.doSomething();
    }
}

