package net.argus.util;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.IOException;

import net.argus.file.Properties;

public class Display {
	
	public static int getWidhtDisplay() {return Toolkit.getDefaultToolkit().getScreenSize().width;}
	public static int getHeightDisplay() {return Toolkit.getDefaultToolkit().getScreenSize().height;}
	
	public static int getWidthMaximumWindowBounds() {return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;}
	public static int getHeightMaximumWindowBounds() {return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;}
	
	public static int getWidhtDisplay(Properties config) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		try {config.setKey("display.size.width", width);}
		catch(IOException e) {}
		return width;
	}
	public static int getHeightDisplay(Properties config) {
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		try {config.setKey("display.size.height", height);}
		catch(IOException e) {}
		return height;
	}
}
