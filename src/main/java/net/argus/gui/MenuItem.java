package net.argus.gui;

import javax.swing.JMenuItem;

import net.argus.lang.Lang;
import net.argus.lang.LangRegistry;

public class MenuItem extends JMenuItem implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1224411470404693508L;
	
	private String name;

	public MenuItem(String name) {
		super();
		this.name = name;
		
		LangRegistry.addElementLanguage(this);
		FontRegistry.addElement(this);
		
		setText();
	}

	@Override
	public void setText() {
		setText(Lang.get(MENUITEM + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "MenuItem";
	}

}
