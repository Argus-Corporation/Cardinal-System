package net.argus.game;

import java.awt.Color;
import java.awt.Point;

public class GamePixel {
	
	private Point point;
	private Color color = Color.GREEN;
	
	public GamePixel(Point point, Color color) {
		this.point = point;
		this.color = color;
	}
	
	public Point getPoint() {return point;}
	public Color getColor() {return color;}

}
