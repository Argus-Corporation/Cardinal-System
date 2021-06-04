package net.argus.game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import net.argus.exception.GameException;
import net.argus.util.CardinalEnum;

public class Matrix {
	
	private static Pixel[][] pixels;
	
	private static List<Point> newPoint = new ArrayList<Point>();
	
	private static int w, h;
	
	public static void load() throws GameException {
		if(pixels == null)
			throw new GameException();
		
		for(int i = 0, x = 0, y = 0; i < w * h; i++, y += x == w-1 ? 1 : 0, x = x < w-1 ? x + 1: 0)
			addPixel(new Pixel(new Point(x, y), Color.BLACK));
		System.out.println("end init");
	}
	
	public static void create(int w, int h) {
		pixels = new Pixel[w][h];
		
		Matrix.w = w;
		Matrix.h = h;
	}
	
	public synchronized static void addPixel(Pixel pix) {
		pixels[pix.getPoint().x][pix.getPoint().y] = pix;
		newPoint.add(pix.getPoint());
	}
	
	public synchronized static Enumeration<Pixel> getPixels() {
		List<Pixel> pixs = new ArrayList<Pixel>();
		for(Point p : newPoint)
			pixs.add(pixels[p.x][p.y]);
			
		//newPoint.clear();
		
		return new CardinalEnum<Pixel>(pixs);
		
		//return new CardinalEnum<Pixel>(ArrayManager.convert(pixels));
	}
	

}
