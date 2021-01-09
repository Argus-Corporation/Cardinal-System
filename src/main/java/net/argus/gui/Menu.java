package net.argus.gui;

import javax.swing.JMenu;

import net.argus.lang.Lang;
import net.argus.lang.LangRegistry;

public class Menu extends JMenu implements GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	public static final String nameType = "menu";

	public Menu(String name) {
		super();
		this.name = nameType + "." + name + ".name";
		
		LangRegistry.addElementLanguage(this);
		setText();
	}

	@Override
	public void setText() {
		setText(Lang.getLang().getElementString(this.name));
	}

}
