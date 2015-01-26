package org.hope6537.note.design.proxy.example1;

public class GamePlayer implements IGamePlayer {

	private String name;

	public GamePlayer(String name) {
		super();
		this.name = name;
	}

	@Override
	public void login(String username, String password) {
		System.out.println(name + "Login");
	}

	@Override
	public void killBoss() {
		System.out.println(name + "KillBoss");
	}

	@Override
	public void upgrade() {
		System.out.println(name + "Upgrade");
	}

}
