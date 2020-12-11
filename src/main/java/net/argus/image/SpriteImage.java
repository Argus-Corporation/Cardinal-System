package net.argus.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class SpriteImage {
	
	private Point start;
	@SuppressWarnings("unused")
	private Point end;
	
	private int width, height;
	
	private BufferedImage img;
	
	public SpriteImage(BufferedImage img, Point start, Point end) {
		this.start = start;
		this.end = end;
		
		this.width = end.x - start.x;
		this.height = end.y - start.y;
		
		this.img = img;
	}
	
	public Color getColor(int x, int y) {
		int clr = img.getRGB(x, y);
		int alpha = (clr >> 24) & 0xff;
		int red = (clr >> 16) & 0xff;
		int green = (clr >> 8) & 0xff;
		int blue = (clr) & 0xff;
		
		return new Color(red, green, blue, alpha);
	}
	
	public void render(Graphics g, float x0, float y0) {
		for(int i = 0, xImg = start.x, yImg = start.y; i < width * height; i++, yImg += xImg == width + start.x ? 1 : 0, xImg = xImg < width + start.x ? xImg + 1: start.x) {
			g.setColor(getColor(xImg, yImg));
			
			float y1 = y0 - height;
			
			g.fillRect((int) x0 + xImg - start.x, (int) y1 + (yImg - start.y), 1, 1);
		}
		
	}
	
	public int getWidth() {return width;}
	public int getHeight() {return height;}

}
