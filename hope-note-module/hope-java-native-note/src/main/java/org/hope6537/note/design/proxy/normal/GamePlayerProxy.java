package org.hope6537.note.design.proxy.normal;

import org.hope6537.note.design.proxy.example1.IGamePlayer;

public class GamePlayerProxy implements IGamePlayer {

    private IGamePlayer gamePlayer;

    public GamePlayerProxy(String name) throws Exception {
        super();
        this.gamePlayer = new GamePlayer(this, name);
    }

    @Override
    public void login(String username, String password) {
        gamePlayer.login(username, password);
    }

    @Override
    public void killBoss() {
        gamePlayer.killBoss();
    }

    @Override
    public void upgrade() {
        gamePlayer.upgrade();
    }

}
