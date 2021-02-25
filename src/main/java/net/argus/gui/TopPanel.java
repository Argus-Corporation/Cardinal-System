package net.argus.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.argus.file.Properties;

public class TopPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8317280304132124519L;
	
	public boolean fullScreen;
	private JLabel labelTitle;
	private static final String esp = "                   ";
	private JLabel iconFrame;
	private static Point compCoords;
	
	@SuppressWarnings("deprecation")
	public TopPanel(Frame fen, ImageIcon icon, boolean[] but, Properties config) {
		super(new BorderLayout());
		this.setBackground(Color.decode("#DDDEDD"));
		
		RondButton titleButton = new RondButton(but, this);
		titleButton.setBounds(new Rectangle(100, 100, 10, 10));
		this.add(BorderLayout.WEST, titleButton.getButtonsPanel(fen, config));
		
		JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
		center.setOpaque(false);
		
		iconFrame = new JLabel(icon);
		center.add(iconFrame);
		
		labelTitle = new JLabel(fen.getTitle() + esp);
		labelTitle.setFont(new Font("arial", 1, 15));
		labelTitle.setForeground(new Color(0, 0, 0, 140));
		
		center.add(labelTitle);
		
		this.add(BorderLayout.CENTER, center);
		
		this.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {compCoords = null;}
            public void mousePressed(MouseEvent e) {compCoords = e.getPoint();}
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			public void mouseDragged(MouseEvent e) {
				if(compCoords != null) {
					Point currCoords = e.getLocationOnScreen();
					if(!fullScreen) fen.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
				}
			}
        });
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		 Graphics2D g2d = (Graphics2D) g;
         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         GradientPaint gp = new GradientPaint(0, 0,
        		 Color.decode("#E4E4E4"), 0, getHeight(),
                 Color.decode("#AAAAAA"));
         g2d.setPaint(gp);
         g2d.fillRect(0, 0, getWidth(), getHeight()); 

	}
	
	public void setIcon(ImageIcon icon) {
		iconFrame.setIcon(icon);;
	}
	
	public void setTitle(String title) {
		labelTitle.setText(title + esp);
	}

}
