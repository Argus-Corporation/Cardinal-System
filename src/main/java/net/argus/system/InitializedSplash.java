package net.argus.system;

import net.argus.file.FileManager;
import net.argus.gui.Icon;
import net.argus.gui.splash.SplashScreen;

public class InitializedSplash implements InitializedSystemManager {
	
	private static SplashScreen splash;
	
	private static String imgPath;
	private static int imgWidth;
	private static int time;
	
	public InitializedSplash(String imgPath, int imgWidth, int time) {
		InitializedSplash.imgPath = imgPath;
		InitializedSplash.imgWidth = imgWidth;
		InitializedSplash.time = time;
	}
	
	public static SplashScreen getSplash() {
		return splash;
	}

	@Override
	public void preInit(String[] args) {
		splash = new SplashScreen(Icon.getIcon(FileManager.getPath(imgPath), imgWidth), time);
		splash.view();
		
	}

	@Override
	public void init(String[] args) {}

	@Override
	public void postInit(String[] args) {}

}
