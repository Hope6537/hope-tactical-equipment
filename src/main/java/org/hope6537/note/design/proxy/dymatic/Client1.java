package org.hope6537.note.design.proxy.dymatic;

import org.hope6537.note.design.proxy.example1.IGamePlayer;
import org.hope6537.note.design.proxy.field.GamePlayer;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Client1 {

    @Test
    public void test() {
        IGamePlayer gamePlayer = new GamePlayer("张三");
        InvocationHandler handler = new DymaticGamePlay(gamePlayer);
        ClassLoader classLoader = gamePlayer.getClass().getClassLoader();
        IGamePlayer proxy = (IGamePlayer) Proxy.newProxyInstance(classLoader,
                new Class[]{IGamePlayer.class}, handler);
        proxy.login("", "");
        proxy.killBoss();
        proxy.upgrade();
    }

}
