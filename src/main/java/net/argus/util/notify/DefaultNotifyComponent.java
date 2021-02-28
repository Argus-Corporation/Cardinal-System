package net.argus.util.notify;

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
	public void show() {
		init = false;
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
		g.drawString(info.getTitle(), minX + 20, minY + 14);
	}
	
	private void drawIcon(Graphics2D g) {
		g.drawImage(Icon.getIcon(info.getIcon(), 16, 16).getImage(), minX, minY, null);
	}
	
	private void drawMessage(Graphics2D g) {
		g.setColor(messageColor);
		g.setFont(titleFont);
		
		FontMetrics metrics = g.getFontMetrics();

		String[] line = getLines(metrics);

		if(!init) {
			if(line.length > 2)
				resize(g, heigth + (15 * (line.length - 2)));
			else
				resize(g, heigth);
			init = true;	
		}
		
		for(int i = 0, j = 20; i < line.length; i++, j += 15)
			g.drawString(line[i], minX, minY + 15 + j);
		
	}
	
	protected void resize(Graphics2D g, int height) {
		setBounds(0, 0, getWidth(), height);
		setPreferredSize(new Dimension(getWidth(), getHeight()));
		
		getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
		getWindow().pack();
	}
	
	protected String[] getLines(FontMetrics metrics) {
		int messWidth = metrics.stringWidth(info.getMessage());
		int nLine = (messWidth / maxWidth);
		
		int of = 0;
		String[] line = new String[nLine + 1];		
		for(int i = 0, j = 0; i < info.getMessage().length() + 1; i++) {
			if(metrics.stringWidth(info.getMessage().substring(of, i)) >= maxWidth) {
				of = i;
				j++;
			}
			line[j] = info.getMessage().substring(of, i);
		}
		
		return line;
	}
	
}
