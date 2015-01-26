package org.hope6537.note.design.proxy.normal;

import org.hope6537.note.design.proxy.example1.IGamePlayer;

public class GamePlayer implements IGamePlayer {

	private String name;

	public GamePlayer(IGamePlayer gamePlayer, String name) throws Exception {
		if (gamePlayer == null) {
			throw new Exception();
		} else {
			this.name = name;
		}
	}

	@Override
	public void login(String username, String password) {
		System.out.println(name + " Login");
	}

	@Override
	public void killBoss() {
		System.out.println(name + " KillBoss");
	}

	@Override
	public void upgrade() {
		System.out.println(name + " Upgrade");
	}

}
