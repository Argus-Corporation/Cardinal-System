package net.argus.gui;

import javax.swing.JCheckBoxMenuItem;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class MenuCheckBoxItem extends JCheckBoxMenuItem implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1594899803049995232L;
	
	private String name;
	
	public MenuCheckBoxItem(String name) {
		super();
		this.name = name;
		
		LangRegister.addElementLanguage(this);
		FontRegister.addElement(this);
		BackgoundRegister.addElement(this);
		ForegroundRegiter.addElement(this);
		
		setText();
	}

	@Override
	public void setText() {
		setText(Lang.get(MENUCHECKITEM + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "CheckBoxMenuItem";
	}

}