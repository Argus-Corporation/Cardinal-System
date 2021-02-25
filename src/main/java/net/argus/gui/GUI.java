package net.argus.gui;

import java.awt.Color;
import java.awt.Font;

public interface GUI {
	
	public static final String BUTTON = "button";
	public static final String CHECKBOX = "checkbox";
	public static final String LABEL = "text";
	public static final String MENU = "menu";
	public static final String MENUCHECKITEM = "menucheckitem";
	public static final String MENUITEM = "menuitem";
	
	public void setForeground(Color fore);
	
	public void setBackground(Color bg);
	
	public void setFont(Font font);
	
	public void setText();
	
	public String getElementName();

}
