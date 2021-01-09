package net.argus.gui;

import javax.swing.JMenuItem;

import net.argus.lang.Lang;
import net.argus.lang.LangRegistry;

public class MenuItem extends JMenuItem implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1224411470404693508L;
	
	public static final String nameType = "menuitem";
	
	private String name;

	public MenuItem(String name) {
		super();
		this.name = name;
		
		LangRegistry.addElementLanguage(this);
		setText();
	}

	@Override
	public void setText() {
		setText(Lang.getLang().getElementString(nameType + "." + name + ".name"));
	}

}
