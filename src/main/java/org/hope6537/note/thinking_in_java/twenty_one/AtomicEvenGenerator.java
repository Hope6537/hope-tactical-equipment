package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使用原子类进行安全递增操作
 * @signdate 2014年8月7日下午4:15:29
 * @company Changchun University&SHXT
 */
public class AtomicEvenGenerator extends IntGenerator {
    private AtomicInteger currentValue = new AtomicInteger(0);

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenGenerator());
    }

    @Override
    public int next() {
        return currentValue.addAndGet(2);
    }
}
