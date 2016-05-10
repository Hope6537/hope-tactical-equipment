package org.hope6537.note.jvm.volatile_;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 有效的volatile场景
 * Created by hope6537 on 16/5/10.
 */
public class Safe {

    //替换为原子类
    public static volatile AtomicInteger race = new AtomicInteger(0);

    public static void increase() {
        race.incrementAndGet();
    }

    private static final int THREAD_COUNT = 20;

    public static void fucked_up(Class receiverClass) throws Throwable {
        Field race = null;
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        MethodType methodType = MethodType.methodType(void.class);
        MethodHandle increase = MethodHandles.lookup().findStatic(receiverClass, "increase", methodType);
        Field[] fields = receiverClass.getFields();
        for (Field field : fields) {
            if (field.getName().equals("race")) {
                race = field;
            }
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                for (int j = 0; j < 10000; j++) {
                    try {
                        increase.invoke();
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
                System.out.println("[" + Thread.currentThread().getName() + "]finished");
            });
        }

        TimeUnit.SECONDS.sleep(5);
        executorService.shutdown();
        while (!executorService.isTerminated()) ;
        System.out.println(race.get(null).toString());


    }

    public static void main(String[] args) throws Throwable {
        fucked_up(Safe.class);
    }

}
