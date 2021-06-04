package net.argus.util;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Display {
	
	public static int getWidth() {return getSize().width;}
	public static int getHeight() {return getSize().height;}
	
	public static Dimension getSize() {return Toolkit.getDefaultToolkit().getScreenSize();}
	
	public static Rectangle getMaximumWindowBounds() {return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();}
	
	public static int getWidthMaximumWindowBounds() {return getMaximumWindowBounds().width;}
	public static int getHeightMaximumWindowBounds() {return getMaximumWindowBounds().height;}
	
	public static boolean isHeadlessInstance() {return GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance();}
	
}
