package net.argus.gui.splash;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

import net.argus.util.ThreadManager;

public class SplashScreen extends JWindow implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -958355071618140591L;
	
	private int time;
	
	private boolean finish;
	
	public SplashScreen(ImageIcon img, boolean alwaysOnTop, int time) {
		this.time = time;
		
		setBackground(new Color(0, 0, 0, 0));
		
		setSize(img.getIconWidth(), img.getIconHeight());
		setLocationRelativeTo(null);
		
		setAlwaysOnTop(alwaysOnTop);
		add(new SplashComponent(img.getImage()));
	}
	
	public SplashScreen(ImageIcon img, int time) {
		this(img, true, time);
	}
	
	public void view() {
		ThreadManager.SPLASH.start(this);
	}

	@Override
	public void run() {
		finish = false;
		
		try {
			setVisible(true);
			Thread.sleep(time);
			setVisible(false);
		}catch(InterruptedException e) {e.printStackTrace();}
		
		finish = true;
	}
	
	public boolean isFinnish() {return finish;}
	
}
