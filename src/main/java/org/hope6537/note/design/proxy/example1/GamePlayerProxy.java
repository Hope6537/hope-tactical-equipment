package org.hope6537.note.design.proxy.example1;

public class GamePlayerProxy implements IGamePlayer {

	private IGamePlayer gamePlayer;

	public GamePlayerProxy(IGamePlayer gamePlayer) {
		super();
		this.gamePlayer = gamePlayer;
	}

	@Override
	public void login(String username, String password) {
		System.out.println("Proxy Has Login");
		gamePlayer.login(username, password);
	}

	@Override
	public void killBoss() {
		System.out.println("Proxy has KillBoss");
		gamePlayer.killBoss();
	}

	@Override
	public void upgrade() {
		System.out.println("Proxy get $5");
		gamePlayer.upgrade();
	}

}
