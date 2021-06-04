package net.argus.gui.top;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import net.argus.file.Properties;
import net.argus.gui.Frame;
import net.argus.gui.animation.FrameAnimation;
import net.argus.gui.top.button.GroupButton;
import net.argus.gui.top.button.TitleButtonType;
import net.argus.system.InitializationSystem;
import net.argus.system.UserSystem;

public class TitleBar extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 463627232347826490L;
	
	private Frame fen;
	
	private Color upColor = Color.decode("#ecebec");
	private Color downColor = Color.decode("#d5d5d5");
	
	private GroupButton groupButton;
	private Title title;
	
	public TitleBar(Frame fen) {
		this.fen = fen;
		this.groupButton = new GroupButton(fen);
		this.title = new Title(fen.getTitle(), fen.getFrameIcon(), this);
		
		setPreferredSize(new Dimension(fen.getWidth(), 35));
		
		groupButton.setBounds(20, 7, groupButton.getPreferredSize().width, groupButton.getPreferredSize().height);
		title.setBounds(0, 0, getPreferredSize().width, getPreferredSize().height);

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
		
		if(!(fen.isMaximized() || fen.isFullScreen())) {
			g2d.fillRoundRect(0, 0, fen.getWidth(), getHeight(), 20, 20);
			g2d.fillRect(0, 20, fen.getWidth(), fen.getHeight());
		}else
			g2d.fillRect(0, 0, fen.getWidth(), getHeight());
		
		super.paintComponent(g);
	}
	
	public void setTitle(String title) {this.title.setTitle(title);}
	public void setIcon(ImageIcon icon) {this.title.setIcon(icon);}
	
	public void setEnabled(TitleButtonType type, boolean b) {groupButton.setEnabled(type, b);}
	
	public Frame getFrame() {return fen;}

	public static void main(String[] args) {
		InitializationSystem.initSystem(args);
		Frame fen = new Frame("Hello World!", "D:\\Django\\Document 1\\Chat\\Project\\res\\favicon16x16.png", new Properties("config", "bin"));
		fen.setDefaultCloseOperation(3);
		fen.setSize(1000, 800);
		fen.setLocationRelativeTo(null);	
		fen.setAnimation(new FrameAnimation(fen));
		
		fen.setVisible(true);
		UserSystem.notify.show("Hello World", "setVisible(true)");
	}

}
