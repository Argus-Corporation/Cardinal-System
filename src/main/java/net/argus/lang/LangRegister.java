package net.argus.lang;

import java.util.ArrayList;
import java.util.List;

import net.argus.gui.GUI;

public class LangRegister {
	
	private static List<GUI> elementGUI = new ArrayList<GUI>();
	
	public static void update() {
		for(GUI gui : elementGUI) {
			gui.setText();
		}
	}
	
	public static void addElementLanguage(GUI element) {
		LangRegister.elementGUI.add(element);
	}

}
