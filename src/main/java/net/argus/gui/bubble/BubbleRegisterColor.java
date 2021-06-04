package net.argus.gui.bubble;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BubbleRegisterColor {
	
	private static final List<BubbleColor> RIGHT_COLORS = new ArrayList<BubbleColor>();
	private static final List<BubbleColor> LEFT_COLORS = new ArrayList<BubbleColor>();
	
	public static void addBubbleColor(int pos, BubbleColor color) {
		switch(pos) {
		case BubblePanel.RIGHT:
			RIGHT_COLORS.add(color);
			break;
		case BubblePanel.LEFT:
			LEFT_COLORS.add(color);
			break;
		}
	}
	
	public static BubbleColor getRight(int index) {
		return RIGHT_COLORS.get(index);
	}
	
	public static BubbleColor getLeft(int index) {
		return LEFT_COLORS.get(index);
	}
	
	static {
		addBubbleColor(BubblePanel.RIGHT, new BubbleColor(Color.decode("#00cd46"), Color.WHITE));
		addBubbleColor(BubblePanel.LEFT, new BubbleColor(Color.decode("#e6e5eb"), Color.BLACK));
		addBubbleColor(BubblePanel.LEFT, new BubbleColor(Color.decode("#404040"), Color.WHITE));
		
	}
	
}

