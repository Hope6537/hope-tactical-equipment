package org.hope6537.note.thinking_in_java.fifteen;

import java.util.Iterator;

/**
 * @version 0.9
 * @Describe 使用适配器模式的斐波那契生成器
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:57:35
 * @company Changchun University&SHXT
 */
public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
    private int n;

    public IterableFibonacci(int count) {
        n = count;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            @Override
            public void remove() {
                System.out.println("Undefined");
            }

            @Override
            public Integer next() {
                n--;
                return IterableFibonacci.this.next();
            }

            @Override
            public boolean hasNext() {

                return n > 0;
            }
        };
    }

}
