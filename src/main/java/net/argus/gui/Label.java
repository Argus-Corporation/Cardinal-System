package net.argus.gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import net.argus.lang.Lang;
import net.argus.lang.LangRegistry;

public class Label extends JLabel implements Element, GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 769636087171939645L;

	private String name;
	
	private static final String elementType = "label";
	private static final boolean isBack = false;
	private static final boolean isFore = true;
	private static final boolean isFont = true;
	
	public Label(String name) {
		this(name, true);
	}
	
	public Label(String name, boolean lang) {
		super();
		common(name, lang);
	}
	
	public Label(ImageIcon icon) {
		super(icon);
		common("", false);
	}
	
	private void common(String name, boolean lang) {
		FontRegistry.addElement(this);
		
		
		nameTypes.add(elementType);
		isBacks.add(isBack);
		isFores.add(isFore);
		isFonts.add(isFont);
		element.add(this);
		if(lang) {
			LangRegistry.addElementLanguage(this);
			this.name = name;
			setText();
		}else
			setText(name);
	}
	
	@Override
	public void setText() {super.setText(Lang.get(LABEL + "." + name + ".name"));}

	@Override
	public String getElementName() {
		return "Label";
	}

}
