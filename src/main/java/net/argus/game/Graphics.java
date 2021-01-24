package net.argus.game;

import java.awt.Color;
import java.awt.Point;

public class Graphics {
	
	private Color color = Color.BLACK;
	
	public void drawRect(int x0, int y0, int width, int height) {
		for(int i = 0, x = 0, y = 0; i < width * height; i++, y += x >= width-1 ? 1 : 0, x = x < width-1 ? x + 1: 0)
			draw(x + x0, y + y0);
	}
	
	public void draw(int x, int y) {
		Matrix.addPixel(new GamePixel(new Point(x, y), color));
	}
	
	public void setColor(Color color) {this.color = color;}

}
