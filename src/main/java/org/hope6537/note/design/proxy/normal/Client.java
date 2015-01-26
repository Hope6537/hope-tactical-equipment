package org.hope6537.note.design.proxy.normal;

import org.hope6537.note.design.proxy.example1.IGamePlayer;
import org.junit.Test;

public class Client {

	@Test
	public void test() throws Exception {
		IGamePlayer proxy = new GamePlayerProxy("Hope6537");
		proxy.login("username", "password");
		proxy.killBoss();
		proxy.upgrade();
	}
	
}
