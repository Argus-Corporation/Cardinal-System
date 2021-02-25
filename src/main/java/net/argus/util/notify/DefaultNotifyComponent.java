package net.argus.util.notify;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import net.argus.gui.Icon;

public class DefaultNotifyComponent extends NotifyComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6458181768352772306L;
	
	private int minX = 10, minY = 10;
	private int width = 300, heigth = 70;
	private int maxWidth = width - minX * 2;
	
	private boolean init = false;
	
	public DefaultNotifyComponent() {
		setPreferredSize(new Dimension(width, heigth));
	}
	
	@Override
	public void repaint() {
		init = true;
		super.repaint();
	}
	
	@Override
	protected void paintBorder(Graphics g) {}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintBorder(g);
		
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		drawTitle(g2d);
		drawIcon(g2d);
		drawMessage(g2d);
	}
	
	private void drawTitle(Graphics2D g) {
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, minX + 20, minY + 14);
	}
	
	private void drawIcon(Graphics2D g) {
		g.drawImage(Icon.getIcon(icon, 16, 16).getImage(), minX, minY, null);
	}
	
	private void drawMessage(Graphics2D g) {
		g.setColor(messageColor);
		g.setFont(titleFont);
		
		FontMetrics metrics = g.getFontMetrics();

		String[] line = getLines(metrics);
		getWindow();
		if(!init)
			for(int i = 2; i < line.length; i++) {
				setPreferredSize(new Dimension(getPreferredSize().width, getPreferredSize().height + 15));
				getBorder().paintBorder(this, g, 0, 0, getPreferredSize().width, getPreferredSize().height + 15);
				getWindow().pack();
				init = true;
			}
		
		for(int i = 0, j = 20; i < line.length; i++, j += 15)
			g.drawString(line[i], minX, minY + 15 + j);
		
	}
	
	private String[] getLines(FontMetrics metrics) {
		int messWidth = metrics.stringWidth(message);
		int nLine = (messWidth / maxWidth);
		
		int of = 0;
		String[] line = new String[nLine + 1];		
		for(int i = 0, j = 0; i < message.length() + 1; i++) {
			if(metrics.stringWidth(message.substring(of, i)) >= maxWidth) {
				of = i;
				j++;
			}
			line[j] = message.substring(of, i);
		}
		
		return line;
	}
	
	public NotifyWindow getWindow() {
		Container cont = this;
		while(!((cont = cont.getParent()) instanceof NotifyWindow)) {}
		
		return (NotifyWindow) cont;
	}
	
}
