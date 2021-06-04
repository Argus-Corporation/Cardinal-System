package net.argus.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

public class BackgoundRegister {
	
	private static List<GUI> elementGUI = new ArrayList<GUI>();
	
	public static void update() {
		for(GUI gui : elementGUI) {
			gui.setBackground(UIManager.getColor(gui.getElementName() + ".background"));
		}
	}
	
	public static void addElement(GUI element) {
		BackgoundRegister.elementGUI.add(element);
	}

}
