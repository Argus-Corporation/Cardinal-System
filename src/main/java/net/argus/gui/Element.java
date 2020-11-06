package net.argus.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.UIManager;

import net.argus.util.CharacterManager;

public interface Element {
	
	public static ArrayList<Component> element = new ArrayList<Component>();
	
	public static ArrayList<String> nameTypes = new ArrayList<String>();
	public static ArrayList<Boolean> isBacks = new ArrayList<Boolean>();
	public static ArrayList<Boolean> isFores = new ArrayList<Boolean>();
	public static ArrayList<Boolean> isFonts = new ArrayList<Boolean>();
	
	
	public static void repaintElement() {
		for(int i = 0; i < element.size(); i++) {
			String type = CharacterManager.toUpperCase(nameTypes.get(i));
			
			if(isBacks.get(i)) {element.get(i).setBackground(UIManager.getColor(type + ".background"));}
			if(isFores.get(i)) {element.get(i).setForeground(UIManager.getColor(type + ".foreground"));}
			if(isFonts.get(i)) {element.get(i).setFont(UIManager.getFont(type + ".font"));}
		}
	}

}
