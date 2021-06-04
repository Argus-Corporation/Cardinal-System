package net.argus.game;

import java.awt.Color;
import java.awt.Point;

public class Pixel {
	
	private Point point;
	private Color color;
	
	public Pixel(Point point, Color color) {
		this.point = point;
		this.color = color;
	}
	
	public Point getPoint() {return point;}
	public Color getColor() {return color;}
	
	public void setPoint(Point point) {this.point = point;}
	public void setColor(Color color) {this.color = color;}

}
