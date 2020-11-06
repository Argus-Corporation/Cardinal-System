package net.argus.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class Bubble extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2391562387698239393L;

	private Label lab;
	
	private int x, y;
	
	public Bubble(Label lab) {
		super();
		this.lab = lab;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		GradientPaint gp = new GradientPaint(250, 0, Color.decode("#0c82fe"), 450, 0, Color.decode("#0c82fe"), true);
		Rectangle2D fm = lab.getFontMetrics(lab.getFont()).getStringBounds(lab.getText(), g2);
		g2.setPaint(gp);
		
		g2.fillRoundRect(x, y, (int) fm.getWidth() + 20, (int) fm.getHeight() + 10, (int) (fm.getHeight()), (int) (fm.getHeight()));
		
		g2.setColor(Color.decode("#fefef9"));
		g2.setFont(lab.getFont());
		g2.drawString(lab.getText(), x + 10, (int) (y + fm.getHeight()));
		
		setPreferredSize(new Dimension((int) (fm.getWidth() + 20), (int) (fm.getHeight() + 10))); 
		super.paintComponent(g);
	}

}
