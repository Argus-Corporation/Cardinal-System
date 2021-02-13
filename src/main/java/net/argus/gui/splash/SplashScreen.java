package net.argus.gui.splash;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JWindow;

import net.argus.gui.gif.GIFComponent;
import net.argus.image.gif.GIF;
import net.argus.util.ThreadManager;

public class SplashScreen extends JWindow implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -958355071618140591L;
	
	private GIFComponent gif;
	
	private int time;
	
	private boolean exit;
	private boolean finish;
	
	public SplashScreen(ImageIcon img, boolean alwaysOnTop, int time) {
		this.time = time;
		setBackground(new Color(0, 0, 0, 0));
		
		setSize(img.getIconWidth(), img.getIconHeight());
		setLocationRelativeTo(null);
		
		setAlwaysOnTop(alwaysOnTop);
		add(new SplashComponent(img.getImage()));
	}
	
	public SplashScreen(GIFComponent gif, boolean alwaysOnTop, int time) {
		this.time = time;
		this.gif = gif;
		
		setBackground(new Color(0, 0, 0, 0));
		setSize(gif.getPreferredSize());
		setLocationRelativeTo(null);
		
		setAlwaysOnTop(alwaysOnTop);
		add(gif);
		gif.start();
	}
	
	public SplashScreen(GIF gif, boolean alwaysOnTop, int time) {
		this(new GIFComponent(gif), alwaysOnTop, time);
	}
	
	public SplashScreen(ImageIcon img, int time) {
		this(img, true, time);
	}
	
	public void view() {
		ThreadManager.SPLASH.start(this);
	}
	
	public void hideSplash() {
		setVisible(false);
	}
	
	public void exit() {
		this.exit = true;
	}

	@Override
	public void run() {
		finish = false;
		setVisible(true);
		
		while(!exit)
			ThreadManager.sleep(1);
		
		ThreadManager.sleep(time);

		setVisible(false);
		if(gif != null) gif.destroy();
		finish = true;
	}
	
	public boolean isFinnish() {return finish;}
	
}
