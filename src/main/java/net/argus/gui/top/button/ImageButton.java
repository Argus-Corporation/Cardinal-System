package net.argus.gui.top.button;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

abstract class ImageButton {
	
	public static ImageButton close = new ImageButton(new Color(70, 70, 70)) {
		public void paint(Graphics2D g) {
			Rectangle rect = new Rectangle(3, 8, 12, 2);
			
			g.rotate(Math.toRadians(45), rect.x + rect.width/2, rect.y + rect.height/2);
		    g.fillRect(rect.x, rect.y, rect.width, rect.height);
		    
		    g.rotate(Math.toRadians(90), rect.x + rect.width/2, rect.y + rect.height/2);
		    g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	};
	
	public static ImageButton minimize = new ImageButton(new Color(70, 70, 70)) {
		public void paint(Graphics2D g) {
			Rectangle rect = new Rectangle(3, 8, 12, 2);
			
		    g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
	};
	
	public static ImageButton expand = new ImageButton(new Color(70, 70, 70)) {
		public void paint(Graphics2D g) {
			int[] xs = new int[] {4, 11, 4};
			int[] ys = new int[] {4, 4, 11};
			
			Polygon poly = new Polygon(xs, ys, xs.length);
			
			g.fillPolygon(poly);
			
			xs = new int[] {13, 13, 6};
			ys = new int[] {13, 6, 13};
			
			poly = new Polygon(xs, ys, xs.length);
			
			g.fillPolygon(poly);
		}
	};
	
	public static ImageButton unexpand = new ImageButton(new Color(70, 70, 70)) {
		public void paint(Graphics2D g) {
			int[] xs = new int[] {8, 8, 2};
			int[] ys = new int[] {8, 2, 8};
			
			Polygon poly = new Polygon(xs, ys, xs.length);
			
			g.fillPolygon(poly);
			
			xs = new int[] {10, 16, 10};
			ys = new int[] {10, 10, 16};
			
			poly = new Polygon(xs, ys, xs.length);
			
			g.fillPolygon(poly);
		}
	};
	
	private Color color;
	
	public ImageButton(Color color) {
		this.color = color;
	}
	
	public ImageButton() {}
	
	public void draw(Graphics2D g) {
		Color c = g.getColor();
		if(color != null) g.setColor(new Color(70, 70, 70));
		
		paint(g);
	    
	    g.dispose();
	    g.setColor(c);
	}
	
	public abstract void paint(Graphics2D g);

}
