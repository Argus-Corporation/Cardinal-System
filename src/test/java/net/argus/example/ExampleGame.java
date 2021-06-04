package net.argus.example;

import java.awt.Color;

import net.argus.exception.GameException;
import net.argus.game.CardiGame;
import net.argus.game.Display;
import net.argus.game.Graphics;
import net.argus.game.Matrix;

public class ExampleGame extends CardiGame {
	
	public ExampleGame() {
		super();
	}

	@Override
	public void init() throws GameException {
		Display.create(1200, 700);
		
		Matrix.create(1200, 700);
		Matrix.load();
		
		Display.setVisible(true);
	}

	int pos = 10;
	
	@Override
	public void update() throws GameException {
		g.setColor(Color.CYAN);
		g.drawRect(pos++, pos++, 10, 10);
	}

	@Override
	public void render(Graphics g) throws GameException {
		Display.repaint();		
	}
	
	public static void main(String[] args) {
		ExampleGame game = new ExampleGame();
		game.setNormalTPS(60);
		game.run();
	}

}
