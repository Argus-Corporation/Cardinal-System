package net.argus.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.argus.lang.Lang;

public class Label extends JLabel implements Element, GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 769636087171939645L;

	private String name;
	
	private static final String nameType = "text";
	private static final String elementType = "label";
	private static final boolean isBack = false;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	private boolean lang;
	
	public Label(String name, boolean lang) {
		super();
		this.name = nameType + "." + name + ".name";
		common(name, lang);
	}
	
	public Label(ImageIcon icon) {
		super(icon);
		common("", false);
	}
	
	private void common(String name, boolean lang) {
		this.lang = lang;
		
		nameTypes.add(elementType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		
		setText();
	}
	
	@Override
	public void setText() {super.setText(lang?Lang.getLang().getElementString(this.name):this.name);}

}
