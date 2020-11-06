package net.argus.gui;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import net.argus.Lang;

public class Button extends JButton implements Element {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3879913755145121820L;
	
	private String name;
	private static final String nameType = "button";
	private static final boolean isBack = true;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	public Button(String name) {
		super();
		this.name = nameType + "." + name + ".name";
		this.setText();
	}
	
	public Button(ImageIcon icon) {
		super(icon);
		nameTypes.add(nameType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		this.setFocusPainted(false);
	}
	
	public void setText() {this.setText(Lang.getLang().getElementString(this.name));}

}
