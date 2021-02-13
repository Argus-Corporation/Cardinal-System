package net.argus.example;

import net.argus.game.CardiGame;
import net.argus.game.Display;
import net.argus.game.Graphics;

public class TestGame extends CardiGame {

	@Override
	public void init() {
		//System.out.println("init");
	}

	@Override
	public void update() {
		//System.out.println("update");
	}

	@Override
	public void render(Graphics g) {
		g.drawRect(10, 10, 100, 200);
	}
	
	public static void main(String[] args) {
		new TestGame().start();
		Display.create(1200, 700);
		Display.setVisible(true);
		
		
	}

}
