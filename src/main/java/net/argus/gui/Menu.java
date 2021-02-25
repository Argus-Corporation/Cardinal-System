package net.argus.gui;

import javax.swing.JMenu;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class Menu extends JMenu implements GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public Menu(String name) {
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
		setText(Lang.get(MENU + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "Menu";
	}

}
