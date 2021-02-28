package net.argus.util.notify;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import net.argus.gui.border.NotifyBorder;

public abstract class NotifyComponent extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209379136830926876L;
	
	protected NotifyInfo info;
	
	protected Color titleColor = new Color(120, 120, 120), messageColor = new Color(70, 70, 70), backgroundColor = new Color(255, 255, 255, 180);
	protected Font titleFont = new Font("roboto", 1, 13);
	
	protected int y = -1;
	
	public NotifyComponent() {
		setBorder(new NotifyBorder(this));
	}
	
	public NotifyWindow getWindow() {
		Container cont = this;
		while(!((cont = cont.getParent()) instanceof NotifyWindow)) {}
		
		return (NotifyWindow) cont;
	}
	
	protected abstract void paintComponent(Graphics g);
	
	public abstract void show();
	
	public NotifyInfo getInfo() {return info;}
	
	public Color getTitleColor() {return titleColor;}
	public Color getMessageColor() {return messageColor;}
	public Color getBackgoundColor() {return backgroundColor;}
	
	public void setInfo(NotifyInfo info) {this.info = info;}
	
	public void setTitleColor(Color title) {this.titleColor = title;}
	public void setMessageColor(Color message) {this.messageColor = message;}
	public void setBackgroundColor(Color background) {this.backgroundColor = background;}

}
