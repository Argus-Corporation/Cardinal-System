package net.argus.gui.bubble;

import java.awt.Color;

public class BubbleColor {
	
	public static final Color BRIGHT = Color.WHITE;
	public static final Color DARK = Color.BLACK;
	
	private Color back;
	
	public BubbleColor(Color back, Color fore) {
		this.back = back;
	}
	
	public Color getBackground() {return back;}
	//public Color getForeground() {return fore;}
	
	public static Color getForeground(Color back) {
		int mid = (back.getRed() + back.getGreen() + back.getBlue()) / 3;
		
		if(mid > 200)
			return DARK;
		else
			return BRIGHT;
		
		
	}

}
