package net.argus.example;

import java.awt.Color;

import net.argus.game.CardiGame;
import net.argus.game.Display;
import net.argus.game.Graphics;
import net.argus.game.Matrix;

public class ExampleGame extends CardiGame {
	
	public ExampleGame() {
		super();
	}

	@Override
	public void init() {
		Display.create(1200, 700);
		
		Matrix.create(1200, 700);
		//Matrix.load();
		
		Display.setVisible(true);
	}

	int pos = 0;
	
	@Override
	public void update() {
		g.setColor(Color.CYAN);
		g.drawRect(pos++, pos++, 100, 100);
	}

	@Override
	public void render(Graphics g) {
		CardiGame.endRender = true;
		Display.repaint();
		CardiGame.endRender = false;
		
	}
	
	public static void main(String[] args) {
		ExampleGame game = new ExampleGame();
		game.setNormalTPS(60);
		game.run();
	}

}
