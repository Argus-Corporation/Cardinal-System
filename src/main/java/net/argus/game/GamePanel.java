package net.argus.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4836710931402058445L;
	
	private static List<GamePixel> pixels = new ArrayList<GamePixel>();
	
	public GamePanel() {
		this.setBackground(Color.BLACK);
	}
	
	public static synchronized void addPixel(GamePixel pixel) {
		if(isPixelSuperposate(pixel)) {
			pixels.remove(pointIndexOf(pixel));
		}
		
		pixels.add(pixel);
	}
	
	public static int pointIndexOf(GamePixel pix) {
		for(int i = 0; i < pixels.size(); i++) {
			GamePixel p = pixels.get(i);
			if(pix.getPoint().x == p.getPoint().x && pix.getPoint().y == p.getPoint().y)
				return i;
		}
		return -1;
	}
	
	public static boolean isPixelSuperposate(GamePixel pix) {
		for(GamePixel p : pixels)
			if(pix.getPoint().x == p.getPoint().x && pix.getPoint().y == p.getPoint().y)
				return true;
		return false;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		//RunTime time = new RunTime();
		
		
		int width = Matrix.getWidth(), height = Matrix.getHeight();
		
		for(int i = 0, x = 0, y = 0; i < width * height; i++, y += x >= width-1 ? 1 : 0, x = x < width-1 ? x + 1: 0) {
			GamePixel pix = Matrix.getPixel(x, y);
			if(pix != null) {
				if(!Matrix.isSuperimposed(x, y)) {
					g.setColor(pix.getColor());
					g.drawLine(pix.getPoint().x, pix.getPoint().y, pix.getPoint().x, pix.getPoint().y);
				}
			}
		}
		
	//System.out.println(Matrix.length());
		Matrix.clear();
		//time.start();
	//	Matrix.transfert();
	//	System.out.println(time.stop());
		/*for(int i = 0; i < Matrix.getNewPixels().size(); i++) {
			GamePixel pix = Matrix.getNewPixels().get(i);
			
			g.setColor(pix.getColor());
			g.drawLine(pix.getPoint().x, pix.getPoint().y, pix.getPoint().x, pix.getPoint().y);
		}
		Matrix.transfert();*/
		
		/*
		int width = Matrix.getWidth(), height = Matrix.getHeight();
		
		for(int i = 0, x = 0, y = 0; i < width * height; i++, y += x >= width-1 ? 1 : 0, x = x < width-1 ? x + 1: 0) {
			GamePixel pix = Matrix.getPixel(x, y);
			if(pix != null) {
				g.setColor(pix.getColor());
				g.drawLine(pix.getPoint().x, pix.getPoint().y, pix.getPoint().x, pix.getPoint().y);
			}
		}
		*/
		CardiGame.endRender = true;
		
	}

}
