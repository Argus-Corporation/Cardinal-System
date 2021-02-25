package net.argus.util.notify;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import net.argus.gui.border.NotifyBorder;

public abstract class NotifyComponent extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209379136830926876L;
	
	protected String title, message;
	
	protected ImageIcon icon;
	
	protected Color titleColor = new Color(120, 120, 120), messageColor = new Color(70, 70, 70), backgroundColor = new Color(255, 255, 255, 180);
	protected Font titleFont = new Font("roboto", 1, 13);
	
	protected int y = -1;
	
	public NotifyComponent() {
		setBorder(new NotifyBorder(this));
	}
	
	protected abstract void paintComponent(Graphics g);
	
	
	public String getTitle() {return title;}
	public String getMessage() {return message;}
	public ImageIcon getIcon() {return icon;}
	
	public Color getTitleColor() {return titleColor;}
	public Color getMessageColor() {return messageColor;}
	public Color getBackgoundColor() {return backgroundColor;}
	
	public void setTitle(String title) {this.title = title.toUpperCase();}
	public void setMessage(String message) {this.message = message;}
	
	public void setIcon(ImageIcon icon) {this.icon = icon;}
	public void setIcon(String iconPath) {this.icon = new ImageIcon(iconPath);}
	public void setIcon(Image image) {this.icon = new ImageIcon(image);}
	
	public void setTitleColor(Color title) {this.titleColor = title;}
	public void setMessageColor(Color message) {this.messageColor = message;}
	public void setBackgroundColor(Color background) {this.backgroundColor = background;}

}
