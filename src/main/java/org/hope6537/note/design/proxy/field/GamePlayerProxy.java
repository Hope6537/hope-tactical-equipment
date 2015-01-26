package org.hope6537.note.design.proxy.field;

public class GamePlayerProxy implements IGamePlayer {

	private IGamePlayer gamePlayer = null;

	public GamePlayerProxy(IGamePlayer gamePlayer) {
		super();
		this.gamePlayer = gamePlayer;
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

	@Override
	public IGamePlayer getProxy() {
		return this;
	}

}
