package org.hope6537.note.design.strategy;

import org.junit.Test;

/**
 * 高层模块
 */
public class Client {

    @Test
    public void test() {
        //挑选策略来执行
        Strategy strategy = new StrategyImpl1();
        Strategy strategy2 = () -> System.out.println("lamada");
        Context context = new Context(strategy2);
        context.doAnyThing();
    }
}
