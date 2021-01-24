package net.argus.gui.splash;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

public class SplashComponent extends JComponent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4889344324436339758L;
	
	private Image img;
	
	public SplashComponent(Image img) {
		this.img = img;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
	

}
