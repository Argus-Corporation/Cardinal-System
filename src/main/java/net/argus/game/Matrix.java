package net.argus.game;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Matrix {
	
	private static GamePixel[][] pixels;
	private static List<GamePixel> newPixels;
	
	private static int width, height;
	
	public static void create(int width, int height) {
		pixels = new GamePixel[width][height];
		newPixels = new ArrayList<GamePixel>();
		
		Matrix.width = width;
		Matrix.height = height;
	}
	
	public static void load() {
		for(int i = 0, x = 0, y = 0; i < width * height; i++, y += x >= width-1 ? 1 : 0, x = x < width-1 ? x + 1: 0) {
			addPixel(new GamePixel(new Point(x, y), Color.BLACK));
		}
	}
	
	public static void addPixel(GamePixel pix) {
		if(!CardiGame.endRender)
			newPixels.add(pix);
	}
	
	public static synchronized boolean isSuperimposed(int x0, int y0) {
		List<GamePixel> pixs = newPixels;
		for(GamePixel pix : pixs)
			if(pix.getPoint().x == x0 && pix.getPoint().y == y0)
				return true;
		return false;
	}
	
	public static synchronized void clear() {
		pixels = null;
		pixels = new GamePixel[width][height];
	}
	
	public static synchronized void transfert() {
		for(int i = 0; i < newPixels.size(); i++) {
			//System.out.println(newPixels.get(i).getPoint().x + "  " + newPixels.get(i).getPoint().x);
			pixels[newPixels.get(i).getPoint().x][newPixels.get(i).getPoint().y] = newPixels.get(i);
		}
		System.out.println("end trans");
		newPixels.clear();
	}
	
	public static GamePixel getPixel(int x, int y) {
		return pixels[x][y];
	}
	
	public static List<GamePixel> getNewPixels() {
		return newPixels;
	}
	
	public static int length() {
		int count = 0;
		for(int i = 0, x = 0, y = 0; i < width * height; i++, y += x >= width-1 ? 1 : 0, x = x < width-1 ? x + 1: 0)
			if(pixels[x][y] != null)
				count++;
		return count;
	}
	
	public static int getWidth() {return width;}
	public static int getHeight() {return height;}
	

}
