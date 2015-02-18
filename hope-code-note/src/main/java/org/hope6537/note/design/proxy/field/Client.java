package org.hope6537.note.design.proxy.field;

import org.junit.Test;

public class Client {

    @Test
    public void test() {
        IGamePlayer gamePlayer = new GamePlayer("Hope6537");
        IGamePlayer proxy = gamePlayer.getProxy();
        proxy.login("1", "");
        proxy.killBoss();
        proxy.upgrade();
    }
}
