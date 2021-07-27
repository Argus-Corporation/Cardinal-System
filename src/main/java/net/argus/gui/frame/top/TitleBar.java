package net.argus.gui.frame.top;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.argus.gui.frame.Frame;
import net.argus.gui.frame.top.button.GroupButton;
import net.argus.gui.frame.top.button.TitleButtonType;

public class TitleBar extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 463627232347826490L;
	
	public static final int HEIGHT = 35;
	
	private Frame fen;
	
	private Color upColor = Color.decode("#ecebec");
	private Color downColor = Color.decode("#d5d5d5");
		
	private GroupButton groupButton;
	private Title title;
	
	public TitleBar(Frame fen) {
		this.fen = fen;
		this.groupButton = new GroupButton(fen);
		this.title = new Title(fen.getTitle(), this);
		
		setSize();

		this.add(groupButton);
		this.add(title);
			
		new Motion(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
		GradientPaint gp = new GradientPaint(0, 0, upColor, 0, getHeight(), downColor);
		g2d.setPaint(gp);
				
		if(!fen.isMaximized()) {
			g2d.fillRoundRect(0, 0, fen.getWidth(), getHeight(), 20, 20);
			g2d.fillRect(0, 20, fen.getWidth(), fen.getHeight());
		}else
			g2d.fillRect(0, 0, fen.getWidth(), getHeight());
		
		super.paintComponent(g2d);
	}
	
	public void setTitle(String title) {this.title.setTitle(title);}
	public void setIcon(ImageIcon icon) {this.title.setIcon(icon);}
	
	public void setEnabled(TitleButtonType type, boolean b) {groupButton.setEnabled(type, b);}
	
	public void setSize() {
		setPreferredSize(new Dimension(fen.getWidth(), HEIGHT));
		
		groupButton.setBounds(20, 7, groupButton.getPreferredSize().width, groupButton.getPreferredSize().height);
		title.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);
	}
	
	public ImageIcon getIcon() {return title.getIcon();}
	
	public Frame getFrame() {return fen;}
	public GroupButton getGroupButton() {return groupButton;}

}
