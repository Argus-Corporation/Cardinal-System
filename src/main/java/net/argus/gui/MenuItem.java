package net.argus.gui;

import javax.swing.JMenuItem;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class MenuItem extends JMenuItem implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1224411470404693508L;
	
	private String name;

	public MenuItem(String name) {
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
		setText(Lang.get(MENUITEM + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "MenuItem";
	}

}
