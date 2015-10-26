package org.hope6537.note.design.strategy;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 高层模块
 */
public class Client {

    @Test
    public void test() throws InterruptedException {
        //挑选策略来执行
        Strategy strategy = new StrategyImpl1();
        Strategy strategy2 = () -> System.out.println("lamada");
        Context context = new Context(strategy2);
        context.doAnyThing();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("change Strategy");
        context.setStrategy(strategy);
        context.doAnyThing();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("change Strategy");
        context.setStrategy(() -> {
            int i = 0;
            while(i!=Integer.MAX_VALUE){
                i++;
            }
            System.out.println(i);
        });
        context.doAnyThing();
    }
}
