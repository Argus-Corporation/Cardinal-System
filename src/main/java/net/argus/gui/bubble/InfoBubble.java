package net.argus.gui.bubble;

import java.awt.Color;
import java.awt.Graphics2D;

class InfoBubble extends Bubble {

	public InfoBubble(String text) {
		super(text);
	}
	
	@Override
	protected String[] getLines(String text, int maxW) {
		if(parent == null)
			return null;

		return super.getLines(text, parent.getWidth() / 2 + parent.getWidth() / 3);
	}
	
	@Override
	public void drawBack(Graphics2D g, int x, int y, BubbleColor color) {}
	
	@Override
	public void drawText(Graphics2D g, int x, int y, Color background) {
		if(parent == null)
			return;
		
		int gW = getGratestWidth(lines);
		super.drawText(g, parent.getWidth() / 2 - gW / 2, y, parent.getBackground());
	}

}
