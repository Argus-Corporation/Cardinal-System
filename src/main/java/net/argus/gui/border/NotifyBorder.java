package net.argus.gui.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

import net.argus.util.notify.NotifyComponent;

public class NotifyBorder implements Border {
	
	private NotifyComponent comp;
	
	public NotifyBorder(NotifyComponent comp) {
		this.comp = comp;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
		g2d.setColor(comp.getBackgoundColor());
		g2d.fillRoundRect(x, y, width - 1, height - 1, 20, 20);
		
		g2d.setColor(comp.getBackgoundColor().darker().darker());
		g2d.drawRoundRect(x, y, width - 1, height - 1, 20, 20);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(0, 0, 0, 0);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

}
