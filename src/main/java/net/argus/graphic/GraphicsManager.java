package net.argus.graphic;

import java.awt.Color;
import java.awt.Graphics;

public class GraphicsManager {
	
	public static void drawCenterText(Graphics g, String text, int w, int h) {
		drawCenterText(g, text, w, h, false, -1, -1, null);
	}
	
	public static void drawCenterText(Graphics g, String text, int w, int h, boolean shadow, int wOff, int hOff, Color shadowColor) {
		int w0 = g.getFontMetrics().stringWidth(text);
		
		int x = (w / 2) - (w0 / 2);
		int y = h / 2;
		
		if(shadow) {
			Color c = g.getColor();
			g.setColor(c);
			
			g.setColor(shadowColor);
			g.drawString(text, x + wOff, y + hOff);
			
			g.setColor(c);
		}
		
		g.drawString(text, x, y);
	}

}
