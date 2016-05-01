package org.hope6537.note.tij.fifteen;

/**
 * @version 0.9
 * @Describe 使用生成器生成斐波那契数列
 * @Author Hope6537(赵鹏)
 * @Signdate 2014-7-19下午01:55:10
 * @company Changchun University&SHXT
 */
public class Fibonacci implements Generator<Integer> {
    private int count = 0;

    public static void main(String[] args) {
        Fibonacci fibonacci = new Fibonacci();
        for (int i = 0; i < 18; i++) {
            System.out.print(fibonacci.next() + " ");
        }
    }

    @Override
    public Integer next() {
        return fib(count++);
    }

    private int fib(int n) {
        if (n < 2) {
            return 1;
        }
        return fib(n - 2) + fib(n - 1);
    }
}
