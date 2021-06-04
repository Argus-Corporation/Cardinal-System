package net.argus.game;

import javax.swing.JFrame;

public class Display {
	
	private static JFrame fen;
	
	public static void create(int width, int height) {
		fen = new JFrame();
		fen.setContentPane(new GameComponent());
		fen.setDefaultCloseOperation(3);
		fen.setSize(width, height);
	}
	
	public static void repaint() {
		fen.getContentPane().repaint();
	}
	
	public static void setVisible(boolean v) {
		fen.setVisible(v);
	}

}
