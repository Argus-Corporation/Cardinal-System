package net.argus.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class ForegroundRegiter {
	private static List<GUI> elementGUI = new ArrayList<GUI>();
	
	public static void update() {
		for(GUI gui : elementGUI) {
			gui.setForeground(UIManager.getColor(gui.getElementName() + ".foreground"));
		}
	}
	
	public static void addElement(GUI element) {
		ForegroundRegiter.elementGUI.add(element);
	}

}
