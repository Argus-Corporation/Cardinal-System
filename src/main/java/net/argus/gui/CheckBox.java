package net.argus.gui;

import javax.swing.JCheckBox;

import net.argus.lang.Lang;
import net.argus.lang.LangRegistry;

public class CheckBox extends JCheckBox implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6751452842929435721L;
	
	private String name;
	
	public CheckBox(String name) {
		super();
		this.name = name;
		
		LangRegistry.addElementLanguage(this);
		FontRegistry.addElement(this);
		
		
		setText();
	}

	@Override
	public void setText() {
		setText(Lang.get(CHECKBOX + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "CheckBox";
	}

}
