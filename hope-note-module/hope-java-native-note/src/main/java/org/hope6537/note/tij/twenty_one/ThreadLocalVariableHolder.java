package org.hope6537.note.tij.twenty_one;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 线程本地存储 意义即为在部不同的线程中，面对相同的变量时，生成不同的存储，他们可以使得你可以将状态和线程关联起来
 * @signdate 2014年8月7日下午5:54:54
 * @company Changchun University&SHXT
 */
public class ThreadLocalVariableHolder {
    // 通常用作静态域存储
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {
        private Random rand = new Random(47);

        protected synchronized Integer initialValue() {
            return rand.nextInt(1000);
        }
    };

    // 在ThreadLocal中，只能使用set或者get来访问对象 他是线程安全的，不需要上锁，不会出现竞争条件
    public static void increment() {
        value.set(value.get() + 1);
    }

    public static Integer get() {
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Accessor(i));
        }
        TimeUnit.MILLISECONDS.sleep(100);
        exec.shutdownNow();
    }
}

class Accessor implements Runnable {
    private final int id;

    public Accessor(int idn) {
        id = idn;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "#" + id + " : " + ThreadLocalVariableHolder.get();
    }
}

/*
 * #0 : 259 #2 : 862 #3 : 694 #4 : 556 #1 : 962 #1 : 963 #1 : 964 #1 : 965 #1 :
 * 966 #1 : 967 #1 : 968 #1 : 969 #1 : 970 #4 : 557 #4 : 558 #1 : 971 #3 : 695
 * #3 : 696 #2 : 863 #0 : 260 #2 : 864 #3 : 697 #1 : 972 #4 : 559 #1 : 973 #3 :
 * 698 #2 : 865 #0 : 261 #2 : 866 #3 : 699 #1 : 974 #4 : 560 #1 : 975 #3 : 700
 * #2 : 867 #0 : 262 #2 : 868 #3 : 701 #1 : 976 #4 : 561 #1 : 977 #3 : 702 #2 :
 * 869 #0 : 263 #2 : 870 #3 : 703 #1 : 978 #4 : 562 #1 : 979 #3 : 704 #2 : 871
 * #0 : 264 #2 : 872 #3 : 705 #1 : 980 #4 : 563 #1 : 981 #3 : 706 #2 : 873 #0 :
 * 265 #2 : 874 #3 : 707 #1 : 982 #4 : 564 #1 : 983 #3 : 708 #2 : 875 #0 : 266
 * #2 : 876 #3 : 709 #1 : 984 #4 : 565 #1 : 985 #3 : 710 #2 : 877 #0 : 267 #2 :
 * 878 #3 : 711 #1 : 986 #4 : 566 #1 : 987 #3 : 712 #2 : 879 #0 : 268 #2 : 880
 * #3 : 713 #1 : 988 #4 : 567 #1 : 989 #3 : 714 #2 : 881 #0 : 269 #2 : 882 #0 :
 * 270 #0 : 271 #0 : 272 #0 : 273 #0 : 274 #0 : 275 #0 : 276 #0 : 277 #0 : 278
 * #0 : 279 #0 : 280 #0 : 281 #0 : 282 #0 : 283 #0 : 284 #0 : 285 #0 : 286 #0 :
 * 287 #0 : 288 #0 : 289 #0 : 290 #0 : 291 #0 : 292 #0 : 293 #0 : 294 #0 : 295
 */
