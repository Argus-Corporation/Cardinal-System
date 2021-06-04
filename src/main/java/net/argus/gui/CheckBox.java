package net.argus.gui;

import javax.swing.JCheckBox;

import net.argus.lang.Lang;
import net.argus.lang.LangRegister;

public class CheckBox extends JCheckBox implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6751452842929435721L;
	
	private String name;
	
	public CheckBox(String name) {
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
		setText(Lang.get(CHECKBOX + "." + name + ".name"));
	}

	@Override
	public String getElementName() {
		return "CheckBox";
	}

}
