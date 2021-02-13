package net.argus.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class FontRegistry {
	
private static List<GUI> elementGUI = new ArrayList<GUI>();
	
	public static void update() {
		for(GUI gui : elementGUI) {
			gui.setFont(UIManager.getFont(gui.getElementName() + ".font"));
		}
	}
	
	public static void addElement(GUI element) {
		FontRegistry.elementGUI.add(element);
	}

}
