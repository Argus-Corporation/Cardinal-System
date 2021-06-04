package net.argus.util.notify;

import java.awt.Image;

import javax.swing.ImageIcon;

public class NotifyInfo {
	
	private String title, message;
	private ImageIcon icon;
	
	/**--GETTERS--**/
	public String getTitle() {return title;}
	public String getMessage() {return message;}	
	
	public ImageIcon getIcon() {return icon;}	
	
	/**--SETTERS--**/
	public void setTitle(String title) {this.title = title.toUpperCase();}
	public void setMessage(String message) {this.message = message;}
	
	public void setIcon(ImageIcon icon) {this.icon = icon;}
	public void setIcon(Image image) {this.icon = new ImageIcon(image);}
	public void setIcon(String iconPath) {this.icon = new ImageIcon(iconPath);}
	
	@Override
	public String toString() {
		return title;
	}

}
