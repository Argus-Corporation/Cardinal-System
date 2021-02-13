package net.argus.system;

import java.awt.Dimension;

import net.argus.file.FileManager;
import net.argus.gui.Icon;
import net.argus.gui.gif.GIFComponent;
import net.argus.gui.splash.SplashScreen;
import net.argus.image.gif.GIF;

public class InitializationSplash implements InitializedSystemManager {
	
	private static SplashScreen splash;
	
	private static GIFComponent gif;
	
	private static String imgPath;
	private static int imgWidth;
	private static int time;
	
	public InitializationSplash(String imgPath, int imgWidth, int time) {
		InitializationSplash.imgPath = imgPath;
		InitializationSplash.imgWidth = imgWidth;
		InitializationSplash.time = time;
	}
	
	public InitializationSplash(GIF gif, Dimension size, int time) {
		InitializationSplash.gif = new GIFComponent(gif);
		InitializationSplash.time = time;
		
		InitializationSplash.gif.setPreferredSize(size);
	}
	
	public InitializationSplash(GIF gif, int width, int time) {
		this(gif, new Dimension(width, width), time);
	}
	
	public static SplashScreen getSplash() {
		return splash;
	}

	@Override
	public void preInit(String[] args) {
		if(gif != null) {
			splash = new SplashScreen(gif, true, time);
			splash.view();
		}else {
			splash = new SplashScreen(Icon.getIcon(FileManager.getPath(imgPath), imgWidth), time);
			splash.view();
		}
		
	}

	@Override
	public void init(String[] args) {}

	@Override
	public void postInit(String[] args) {}

}
