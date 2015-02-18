package org.hope6537.note.design.proxy.example1;

import org.junit.Test;

public class Client {

    @Test
    public void test() {
        IGamePlayer gamePlayer = new GamePlayer("Hope");
        IGamePlayer proxy = new GamePlayerProxy(gamePlayer);
        proxy.login("username", "password");
        proxy.killBoss();
        proxy.upgrade();
    }

}
